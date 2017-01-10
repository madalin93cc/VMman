package ro.upb.dai.mcc.vmman.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import ro.upb.dai.mcc.vmman.security.AuthoritiesConstants;
import ro.upb.dai.mcc.vmman.service.OperatingSystemService;
import ro.upb.dai.mcc.vmman.web.rest.util.HeaderUtil;
import ro.upb.dai.mcc.vmman.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OperatingSystem.
 */
@RestController
@RequestMapping("/api")
@Secured(AuthoritiesConstants.ADMIN)
public class OperatingSystemResource {

    private final Logger log = LoggerFactory.getLogger(OperatingSystemResource.class);

    @Inject
    private OperatingSystemService operatingSystemService;

    /**
     * POST  /operating-systems : Create a new operatingSystem.
     *
     * @param operatingSystem the operatingSystem to create
     * @return the ResponseEntity with status 201 (Created) and with body the new operatingSystem, or with status 400 (Bad Request) if the operatingSystem has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/operating-systems")
    public ResponseEntity<OperatingSystem> createOperatingSystem(@Valid @RequestBody OperatingSystem operatingSystem) throws URISyntaxException {
        log.debug("REST request to save OperatingSystem : {}", operatingSystem);
        if (operatingSystem.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("operatingSystem", "idexists", "A new operatingSystem cannot already have an ID")).body(null);
        }
        OperatingSystem result = operatingSystemService.save(operatingSystem);
        return ResponseEntity.created(new URI("/api/operating-systems/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("operatingSystem", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /operating-systems : Updates an existing operatingSystem.
     *
     * @param operatingSystem the operatingSystem to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated operatingSystem,
     * or with status 400 (Bad Request) if the operatingSystem is not valid,
     * or with status 500 (Internal Server Error) if the operatingSystem couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/operating-systems")
    public ResponseEntity<OperatingSystem> updateOperatingSystem(@Valid @RequestBody OperatingSystem operatingSystem) throws URISyntaxException {
        log.debug("REST request to update OperatingSystem : {}", operatingSystem);
        if (operatingSystem.getId() == null) {
            return createOperatingSystem(operatingSystem);
        }
        OperatingSystem result = operatingSystemService.save(operatingSystem);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("operatingSystem", operatingSystem.getId().toString()))
            .body(result);
    }

    /**
     * GET  /operating-systems : get all the operatingSystems.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of operatingSystems in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/operating-systems")
    @Secured({AuthoritiesConstants.USER, AuthoritiesConstants.MANAGER, AuthoritiesConstants.ADMIN})
    public ResponseEntity<List<OperatingSystem>> getAllOperatingSystems(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of OperatingSystems");
        Page<OperatingSystem> page = operatingSystemService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/operating-systems");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /operating-systems/:id : get the "id" operatingSystem.
     *
     * @param id the id of the operatingSystem to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the operatingSystem, or with status 404 (Not Found)
     */
    @GetMapping("/operating-systems/{id}")
    public ResponseEntity<OperatingSystem> getOperatingSystem(@PathVariable Long id) {
        log.debug("REST request to get OperatingSystem : {}", id);
        OperatingSystem operatingSystem = operatingSystemService.findOne(id);
        return Optional.ofNullable(operatingSystem)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /operating-systems/:id : delete the "id" operatingSystem.
     *
     * @param id the id of the operatingSystem to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/operating-systems/{id}")
    public ResponseEntity<Void> deleteOperatingSystem(@PathVariable Long id) {
        log.debug("REST request to delete OperatingSystem : {}", id);
        Boolean result = operatingSystemService.delete(id);
        if (!result) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("operatingSystem", "relationshipexists", "The record cannot be deleted because it is used by other enitities.")).body(null);
        } else {
            return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("operatingSystem", id.toString())).build();
        }
    }

}

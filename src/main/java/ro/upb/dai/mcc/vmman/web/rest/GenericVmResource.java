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
import ro.upb.dai.mcc.vmman.domain.GenericVm;
import ro.upb.dai.mcc.vmman.security.AuthoritiesConstants;
import ro.upb.dai.mcc.vmman.service.GenericVmService;
import ro.upb.dai.mcc.vmman.service.dto.GenericVmDTO;
import ro.upb.dai.mcc.vmman.web.rest.util.HeaderUtil;
import ro.upb.dai.mcc.vmman.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GenericVm.
 */
@RestController
@RequestMapping("/api")
public class GenericVmResource {

    private final Logger log = LoggerFactory.getLogger(GenericVmResource.class);

    @Inject
    private GenericVmService genericVmService;

    /**
     * POST  /virtual-machines : Create a new genericVm.
     *
     * @param genericVm the genericVm to create
     * @return the ResponseEntity with status 201 (Created) and with body the new genericVm, or with status 400 (Bad Request) if the genericVm has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/generic-vms")
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<GenericVmDTO> createGenericVm(@Valid @RequestBody GenericVm genericVm) throws URISyntaxException {
        log.debug("REST request to save GenericVm : {}", genericVm);
        if (genericVm.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("genericVm", "idexists", "A new genericVm cannot already have an ID")).body(null);
        }
        GenericVmDTO result = genericVmService.save(genericVm);
        return ResponseEntity.created(new URI("/api/generic-vms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("genericVm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /generic-vms : Updates an existing genericVm.
     *
     * @param genericVm the genericVm to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated genericVm,
     * or with status 400 (Bad Request) if the genericVm is not valid,
     * or with status 500 (Internal Server Error) if the genericVm couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/generic-vms")
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<GenericVmDTO> updateGenericVm(@Valid @RequestBody GenericVm genericVm) throws URISyntaxException {
        log.debug("REST request to update GenericVm : {}", genericVm);
        if (genericVm.getId() == null) {
            return createGenericVm(genericVm);
        }
        GenericVmDTO result = genericVmService.save(genericVm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("genericVm", genericVm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /generic-vms : get all the genericVms.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of genericVms in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/generic-vms")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.MANAGER, AuthoritiesConstants.USER})
    public ResponseEntity<List<GenericVmDTO>> getAllGenericVms(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of GenericVms");
        Page<GenericVmDTO> page = genericVmService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/generic-vms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /generic-vms/:id : get the "id" genericVm.
     *
     * @param id the id of the genericVm to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the genericVm, or with status 404 (Not Found)
     */
    @GetMapping("/generic-vms/{id}")
    @Secured({AuthoritiesConstants.ADMIN, AuthoritiesConstants.MANAGER, AuthoritiesConstants.USER})
    public ResponseEntity<GenericVmDTO> getGenericVm(@PathVariable Long id) {
        log.debug("REST request to get GenericVm : {}", id);
        GenericVmDTO genericVm = genericVmService.findOne(id);
        return Optional.ofNullable(genericVm)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /generic-vms/:id : delete the "id" genericVm.
     *
     * @param id the id of the genericVm to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/generic-vms/{id}")
    @Secured({AuthoritiesConstants.ADMIN})
    public ResponseEntity<Void> deleteGenericVm(@PathVariable Long id) {
        log.debug("REST request to delete GenericVm : {}", id);
        genericVmService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("genericVm", id.toString())).build();
    }

}

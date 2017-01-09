package ro.upb.dai.mcc.vmman.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import ro.upb.dai.mcc.vmman.service.VirtualMachineService;
import ro.upb.dai.mcc.vmman.service.dto.VirtualMachineDTO;
import ro.upb.dai.mcc.vmman.web.rest.util.HeaderUtil;
import ro.upb.dai.mcc.vmman.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VirtualMachine.
 */
@RestController
@RequestMapping("/api")
public class VirtualMachineResource {

    private final Logger log = LoggerFactory.getLogger(VirtualMachineResource.class);

    @Inject
    private VirtualMachineService virtualMachineService;

    /**
     * POST  /virtual-machines : Create a new virtualMachine.
     *
     * @param virtualMachine the virtualMachine to create
     * @return the ResponseEntity with status 201 (Created) and with body the new virtualMachine, or with status 400 (Bad Request) if the virtualMachine has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/virtual-machines")
    public ResponseEntity<VirtualMachineDTO> createVirtualMachine(@Valid @RequestBody VirtualMachine virtualMachine) throws URISyntaxException {
        log.debug("REST request to save VirtualMachine : {}", virtualMachine);
        if (virtualMachine.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("virtualMachine", "idexists", "A new virtualMachine cannot already have an ID")).body(null);
        }
        VirtualMachineDTO result = virtualMachineService.save(virtualMachine);
        return ResponseEntity.created(new URI("/api/virtual-machines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("virtualMachine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /virtual-machines : Updates an existing virtualMachine.
     *
     * @param virtualMachine the virtualMachine to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated virtualMachine,
     * or with status 400 (Bad Request) if the virtualMachine is not valid,
     * or with status 500 (Internal Server Error) if the virtualMachine couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/virtual-machines")
    public ResponseEntity<VirtualMachineDTO> updateVirtualMachine(@Valid @RequestBody VirtualMachine virtualMachine) throws URISyntaxException {
        log.debug("REST request to update VirtualMachine : {}", virtualMachine);
        if (virtualMachine.getId() == null) {
            return createVirtualMachine(virtualMachine);
        }
        VirtualMachineDTO result = virtualMachineService.save(virtualMachine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("virtualMachine", virtualMachine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /virtual-machines : get all the virtualMachines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of virtualMachines in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/virtual-machines")
    public ResponseEntity<List<VirtualMachineDTO>> getAllVirtualMachines(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VirtualMachines");
        Page<VirtualMachineDTO> page = virtualMachineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/virtual-machines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /virtual-machines/:id : get the "id" virtualMachine.
     *
     * @param id the id of the virtualMachine to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the virtualMachine, or with status 404 (Not Found)
     */
    @GetMapping("/virtual-machines/{id}")
    public ResponseEntity<VirtualMachineDTO> getVirtualMachine(@PathVariable Long id) {
        log.debug("REST request to get VirtualMachine : {}", id);
        VirtualMachineDTO virtualMachine = virtualMachineService.findOne(id);
        return Optional.ofNullable(virtualMachine)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /virtual-machines/:id : delete the "id" virtualMachine.
     *
     * @param id the id of the virtualMachine to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/virtual-machines/{id}")
    public ResponseEntity<Void> deleteVirtualMachine(@PathVariable Long id) {
        log.debug("REST request to delete VirtualMachine : {}", id);
        virtualMachineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("virtualMachine", id.toString())).build();
    }

}

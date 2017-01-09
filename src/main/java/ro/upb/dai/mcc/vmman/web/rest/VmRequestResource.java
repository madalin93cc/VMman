package ro.upb.dai.mcc.vmman.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.upb.dai.mcc.vmman.domain.VmRequest;
import ro.upb.dai.mcc.vmman.service.VmRequestService;
import ro.upb.dai.mcc.vmman.service.dto.VmRequestDTO;
import ro.upb.dai.mcc.vmman.web.rest.util.HeaderUtil;
import ro.upb.dai.mcc.vmman.web.rest.util.PaginationUtil;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing VmRequest.
 */
@RestController
@RequestMapping("/api")
public class VmRequestResource {

    private final Logger log = LoggerFactory.getLogger(VmRequestResource.class);

    @Inject
    private VmRequestService vmRequestService;

    /**
     * POST  /vm-requests : Create a new vmRequest.
     *
     * @param vmRequest the vmRequest to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vmRequest, or with status 400 (Bad Request) if the vmRequest has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vm-requests")
    public ResponseEntity<VmRequestDTO> createVmRequest(@Valid @RequestBody VmRequest vmRequest) throws URISyntaxException {
        log.debug("REST request to save VmRequest : {}", vmRequest);
        if (vmRequest.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vmRequest", "idexists", "A new vmRequest cannot already have an ID")).body(null);
        }
        VmRequestDTO result = vmRequestService.save(vmRequest);
        return ResponseEntity.created(new URI("/api/vm-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vmRequest", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vm-requests : Updates an existing vmRequest.
     *
     * @param vmRequest the vmRequest to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vmRequest,
     * or with status 400 (Bad Request) if the vmRequest is not valid,
     * or with status 500 (Internal Server Error) if the vmRequest couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vm-requests")
    public ResponseEntity<VmRequestDTO> updateVmRequest(@Valid @RequestBody VmRequest vmRequest) throws URISyntaxException {
        log.debug("REST request to update VmRequest : {}", vmRequest);
        if (vmRequest.getId() == null) {
            return createVmRequest(vmRequest);
        }
        VmRequestDTO result = vmRequestService.save(vmRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vmRequest", vmRequest.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vm-requests : get all the vmRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vmRequests in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/vm-requests")
    public ResponseEntity<List<VmRequestDTO>> getAllVmRequests(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VmRequests");
        Page<VmRequestDTO> page = vmRequestService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vm-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vm-requests/:id : get the "id" vmRequest.
     *
     * @param id the id of the vmRequest to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vmRequest, or with status 404 (Not Found)
     */
    @GetMapping("/vm-requests/{id}")
    public ResponseEntity<VmRequestDTO> getVmRequest(@PathVariable Long id) {
        log.debug("REST request to get VmRequest : {}", id);
        VmRequestDTO vmRequest = vmRequestService.findOne(id);
        return Optional.ofNullable(vmRequest)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vm-requests/:id : delete the "id" vmRequest.
     *
     * @param id the id of the vmRequest to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vm-requests/{id}")
    public ResponseEntity<Void> deleteVmRequest(@PathVariable Long id) {
        log.debug("REST request to delete VmRequest : {}", id);
        vmRequestService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vmRequest", id.toString())).build();
    }

    @PutMapping("/vm-requests/{id}")
    public ResponseEntity<Void> approveRequest(@PathVariable Long id) {
        log.debug("REST request to approve VmRequest : {}", id);
        vmRequestService.approve(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("vmRequest", id.toString())).build();
    }

}

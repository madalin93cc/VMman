package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.VmRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.upb.dai.mcc.vmman.service.dto.VmRequestDTO;

import java.util.List;

/**
 * Service Interface for managing VmRequest.
 */
public interface VmRequestService {

    /**
     * Save a vmRequest.
     *
     * @param vmRequest the entity to save
     * @return the persisted entity
     */
    VmRequestDTO save(VmRequest vmRequest);

    /**
     *  Get all the vmRequests.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VmRequestDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" vmRequest.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VmRequestDTO findOne(Long id);

    /**
     *  Delete the "id" vmRequest.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Approve the "id" vmRequest
     *
     * @param id the id of the entity
     */
    void approve(Long id);
}

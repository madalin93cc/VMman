package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.GenericVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.upb.dai.mcc.vmman.service.dto.GenericVmDTO;

/**
 * Service Interface for managing GenericVm.
 */
public interface GenericVmService {

    /**
     * Save a genericVm.
     *
     * @param genericVm the entity to save
     * @return the persisted entity
     */
    GenericVmDTO save(GenericVm genericVm);

    /**
     * Get all the genericVms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<GenericVmDTO> findAll(Pageable pageable);

    /**
     * Get the "id" genericVm.
     *
     * @param id the id of the entity
     * @return the entity
     */
    GenericVmDTO findOne(Long id);

    /**
     * Delete the "id" genericVm.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

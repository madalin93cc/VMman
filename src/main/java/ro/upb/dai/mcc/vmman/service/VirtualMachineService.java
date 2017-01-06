package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing VirtualMachine.
 */
public interface VirtualMachineService {

    /**
     * Save a virtualMachine.
     *
     * @param virtualMachine the entity to save
     * @return the persisted entity
     */
    VirtualMachine save(VirtualMachine virtualMachine);

    /**
     *  Get all the virtualMachines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<VirtualMachine> findAll(Pageable pageable);

    /**
     *  Get the "id" virtualMachine.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    VirtualMachine findOne(Long id);

    /**
     *  Delete the "id" virtualMachine.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

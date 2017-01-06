package ro.upb.dai.mcc.vmman.service.impl;

import ro.upb.dai.mcc.vmman.service.VirtualMachineService;
import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import ro.upb.dai.mcc.vmman.repository.VirtualMachineRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing VirtualMachine.
 */
@Service
@Transactional
public class VirtualMachineServiceImpl implements VirtualMachineService{

    private final Logger log = LoggerFactory.getLogger(VirtualMachineServiceImpl.class);
    
    @Inject
    private VirtualMachineRepository virtualMachineRepository;

    /**
     * Save a virtualMachine.
     *
     * @param virtualMachine the entity to save
     * @return the persisted entity
     */
    public VirtualMachine save(VirtualMachine virtualMachine) {
        log.debug("Request to save VirtualMachine : {}", virtualMachine);
        VirtualMachine result = virtualMachineRepository.save(virtualMachine);
        return result;
    }

    /**
     *  Get all the virtualMachines.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<VirtualMachine> findAll(Pageable pageable) {
        log.debug("Request to get all VirtualMachines");
        Page<VirtualMachine> result = virtualMachineRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one virtualMachine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public VirtualMachine findOne(Long id) {
        log.debug("Request to get VirtualMachine : {}", id);
        VirtualMachine virtualMachine = virtualMachineRepository.findOne(id);
        return virtualMachine;
    }

    /**
     *  Delete the  virtualMachine by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VirtualMachine : {}", id);
        virtualMachineRepository.delete(id);
    }
}

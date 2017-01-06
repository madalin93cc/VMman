package ro.upb.dai.mcc.vmman.service.impl;

import ro.upb.dai.mcc.vmman.service.OperatingSystemService;
import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import ro.upb.dai.mcc.vmman.repository.OperatingSystemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing OperatingSystem.
 */
@Service
@Transactional
public class OperatingSystemServiceImpl implements OperatingSystemService{

    private final Logger log = LoggerFactory.getLogger(OperatingSystemServiceImpl.class);
    
    @Inject
    private OperatingSystemRepository operatingSystemRepository;

    /**
     * Save a operatingSystem.
     *
     * @param operatingSystem the entity to save
     * @return the persisted entity
     */
    public OperatingSystem save(OperatingSystem operatingSystem) {
        log.debug("Request to save OperatingSystem : {}", operatingSystem);
        OperatingSystem result = operatingSystemRepository.save(operatingSystem);
        return result;
    }

    /**
     *  Get all the operatingSystems.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<OperatingSystem> findAll(Pageable pageable) {
        log.debug("Request to get all OperatingSystems");
        Page<OperatingSystem> result = operatingSystemRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one operatingSystem by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public OperatingSystem findOne(Long id) {
        log.debug("Request to get OperatingSystem : {}", id);
        OperatingSystem operatingSystem = operatingSystemRepository.findOne(id);
        return operatingSystem;
    }

    /**
     *  Delete the  operatingSystem by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OperatingSystem : {}", id);
        operatingSystemRepository.delete(id);
    }
}

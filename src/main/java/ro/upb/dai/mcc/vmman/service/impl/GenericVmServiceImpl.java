package ro.upb.dai.mcc.vmman.service.impl;

import ro.upb.dai.mcc.vmman.service.GenericVmService;
import ro.upb.dai.mcc.vmman.domain.GenericVm;
import ro.upb.dai.mcc.vmman.repository.GenericVmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upb.dai.mcc.vmman.service.dto.GenericVmDTO;


/**
 * Service Implementation for managing GenericVm.
 */
@Service
@Transactional
public class GenericVmServiceImpl implements GenericVmService{

    private final Logger log = LoggerFactory.getLogger(GenericVmServiceImpl.class);

    private final GenericVmRepository genericVmRepository;

    public GenericVmServiceImpl(GenericVmRepository genericVmRepository) {
        this.genericVmRepository = genericVmRepository;
    }

    /**
     * Save a genericVm.
     *
     * @param genericVm the entity to save
     * @return the persisted entity
     */
    @Override
    public GenericVmDTO save(GenericVm genericVm) {
        log.debug("Request to save GenericVm : {}", genericVm);
        return new GenericVmDTO(genericVmRepository.save(genericVm));
    }

    /**
     * Get all the genericVms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GenericVmDTO> findAll(Pageable pageable) {
        log.debug("Request to get all GenericVms");
        return genericVmRepository.findAll(pageable).map(GenericVmDTO::new);
    }

    /**
     * Get one genericVm by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GenericVmDTO findOne(Long id) {
        log.debug("Request to get GenericVm : {}", id);
        return new GenericVmDTO(genericVmRepository.findOne(id));
    }

    /**
     * Delete the genericVm by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GenericVm : {}", id);
        genericVmRepository.delete(id);
    }
}

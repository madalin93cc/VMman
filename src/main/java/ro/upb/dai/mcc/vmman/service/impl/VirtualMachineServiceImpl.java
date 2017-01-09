package ro.upb.dai.mcc.vmman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.User;
import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import ro.upb.dai.mcc.vmman.repository.UserRepository;
import ro.upb.dai.mcc.vmman.repository.VirtualMachineRepository;
import ro.upb.dai.mcc.vmman.security.AuthoritiesConstants;
import ro.upb.dai.mcc.vmman.security.SecurityUtils;
import ro.upb.dai.mcc.vmman.service.VirtualMachineService;
import ro.upb.dai.mcc.vmman.service.dto.VirtualMachineDTO;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Service Implementation for managing VirtualMachine.
 */
@Service
@Transactional
public class VirtualMachineServiceImpl implements VirtualMachineService{

    private final Logger log = LoggerFactory.getLogger(VirtualMachineServiceImpl.class);

    @Inject
    private VirtualMachineRepository virtualMachineRepository;

    @Inject
    private UserRepository userRepository;

   /**
     * Save a virtualMachine.
     *
     * @param virtualMachine the entity to save
     * @return the persisted entity
     */
    public VirtualMachineDTO save(VirtualMachine virtualMachine) {
        log.debug("Request to save VirtualMachine : {}", virtualMachine);
        VirtualMachineDTO result = new VirtualMachineDTO(virtualMachineRepository.save(virtualMachine));
        return result;
    }

    /**
     *  Get all the virtualMachines.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VirtualMachineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VirtualMachines");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        Page<VirtualMachineDTO> result = null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            result = virtualMachineRepository.findAll(pageable).map(VirtualMachineDTO::new);
        } else {
            Department department = user.getDepartment();
            if (department != null) {
                result = virtualMachineRepository.findAllByProjectDepartment(pageable, department).map(VirtualMachineDTO::new);
            } else {
                result = new PageImpl<>(Collections.emptyList());
            }
        }
        return result;
    }

    /**
     *  Get one virtualMachine by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VirtualMachineDTO findOne(Long id) {
        log.debug("Request to get VirtualMachine : {}", id);
        VirtualMachineDTO virtualMachine = new VirtualMachineDTO(virtualMachineRepository.findOne(id));
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

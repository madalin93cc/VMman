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
import ro.upb.dai.mcc.vmman.domain.VmRequest;
import ro.upb.dai.mcc.vmman.repository.UserRepository;
import ro.upb.dai.mcc.vmman.repository.VirtualMachineRepository;
import ro.upb.dai.mcc.vmman.repository.VmRequestRepository;
import ro.upb.dai.mcc.vmman.security.AuthoritiesConstants;
import ro.upb.dai.mcc.vmman.security.SecurityUtils;
import ro.upb.dai.mcc.vmman.service.MailService;
import ro.upb.dai.mcc.vmman.service.VmRequestService;
import ro.upb.dai.mcc.vmman.service.dto.VmRequestDTO;
import ro.upb.dai.mcc.vmman.service.util.SimpleMailMessageBuilder;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing VmRequest.
 */
@Service
@Transactional
public class VmRequestServiceImpl implements VmRequestService{

    private final Logger log = LoggerFactory.getLogger(VmRequestServiceImpl.class);

    @Inject
    private VmRequestRepository vmRequestRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private VirtualMachineRepository virtualMachineRepository;

    @Inject
    private MailService mailService;

    /**
     * Save a vmRequest.
     *
     * @param vmRequest the entity to save
     * @return the persisted entity
     */
    public VmRequestDTO save(VmRequest vmRequest) {
        log.debug("Request to save VmRequest : {}", vmRequest);
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        vmRequest.from(user);
        vmRequest.created(false);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            vmRequest.approved(false);
            Department department = user.getDepartment();
            if (department != null) {
                vmRequest.to(department.getManager());
                mailService.sendSimpleMessage(
                    new SimpleMailMessageBuilder(department.getManager().getEmail(),
                        "New Virtual Machine Request",
                        "A request for a new virtual machine has been created. You can go to VMman application and review it.")
                        .build()
                );
            }
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)){
            if (vmRequest.getId() == null) {
                vmRequest.approved(true);
            }

            User admin = userRepository.findOneByLogin("admin").get();
            mailService.sendSimpleMessage(
                new SimpleMailMessageBuilder(admin.getEmail(),
                    "New Virtual Machine Request",
                    "A request for a new virtual machine has been created. You can go to VMman application and review it.")
                    .build()
            );
        }

        VmRequest result = vmRequestRepository.save(vmRequest);
        return new VmRequestDTO(result);
    }

    /**
     *  Get all the vmRequests.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<VmRequestDTO> findAll(Pageable pageable) {
        log.debug("Request to get all VmRequests");
        User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
        Page<VmRequestDTO> result = null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
            result = vmRequestRepository.findAll(pageable).map(VmRequestDTO::new);
        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)){
            Department department = user.getDepartment();
            if (department != null) {
                result = vmRequestRepository.findAllByFromDepartment(pageable, department).map(VmRequestDTO::new);
            } else {
                result = new PageImpl<>(Collections.emptyList());
            }
        } else {
            List<VmRequest> requests = vmRequestRepository.findByFromIsCurrentUser();
            result = new PageImpl<>(requests.stream().map(VmRequestDTO::new).collect(Collectors.toList()));
        }
        return result;
    }

    /**
     *  Get one vmRequest by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public VmRequestDTO findOne(Long id) {
        log.debug("Request to get VmRequest : {}", id);
        VmRequest vmRequest = vmRequestRepository.findOne(id);
        if (vmRequest == null) {
            return null;
        } else {
            return new VmRequestDTO(vmRequest);
        }
    }

    /**
     *  Delete the  vmRequest by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete VmRequest : {}", id);
        vmRequestRepository.delete(id);
    }

    /**
     *  Approve the  vmRequest by id.
     *
     *  @param id the id of the entity
     */
    public void approve(Long id) {
        VmRequest request = vmRequestRepository.findOne(id);
        if (request != null) {
            User user = userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).get();
            if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER)) {
                request.approved(true);
                mailService.sendSimpleMessage(
                    new SimpleMailMessageBuilder(request.getFrom().getEmail(),
                        "Virtual Machine Request was Approved",
                        "Your virtual machine request was approved by the manager of your department. You can check the status on VMman application")
                        .build()
                );
                User admin = userRepository.findOneByLogin("admin").get();
                mailService.sendSimpleMessage(
                    new SimpleMailMessageBuilder(admin.getEmail(),
                        "New Virtual Machine Request",
                        "A request for a new virtual machine has been created. You can go to VMman application and review it.")
                        .build()
                );
            } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)){
                if (request.isApproved()) {
                    VirtualMachine virtualMachine = new VirtualMachine();
                    virtualMachine.setName(request.getName());
                    virtualMachine.setDescription(request.getDescription());
                    virtualMachine.setHdd(request.getHdd());
                    virtualMachine.setProcessor(request.getProcessor());
                    virtualMachine.setRam(request.getRam());
                    virtualMachine.setOperatingSystem(request.getOperatingSystem());
                    virtualMachine.setProject(request.getProject());
                    virtualMachine.setEnvironment(request.getEnvironment());
                    virtualMachineRepository.save(virtualMachine);
                    request.setCreated(true);
                    mailService.sendSimpleMessage(
                        new SimpleMailMessageBuilder(request.getFrom().getEmail(),
                            "Virtual Machine was Created",
                            "Your virtual machine was created. You can check the details of your new virtual machine on VMman application")
                            .build()
                    );

                    mailService.sendSimpleMessage(
                        new SimpleMailMessageBuilder(request.getFrom().getDepartment().getManager().getEmail(),
                            "Virtual Machine was Created",
                            "A new virtual machine was created for your department. You can check the details of the new virtual machine on VMman application")
                            .build()
                    );
                }
            }
            vmRequestRepository.save(request);
        }
    }
}

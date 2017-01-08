package ro.upb.dai.mcc.vmman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.upb.dai.mcc.vmman.domain.Authority;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.Project;
import ro.upb.dai.mcc.vmman.domain.User;
import ro.upb.dai.mcc.vmman.repository.AuthorityRepository;
import ro.upb.dai.mcc.vmman.repository.ProjectRepository;
import ro.upb.dai.mcc.vmman.repository.UserRepository;
import ro.upb.dai.mcc.vmman.security.AuthoritiesConstants;
import ro.upb.dai.mcc.vmman.service.ProjectService;
import ro.upb.dai.mcc.vmman.service.dto.ProjectDTO;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    public ProjectDTO save(Project project) {
        log.debug("Request to save Project : {}", project);
        ProjectDTO result = new ProjectDTO(projectRepository.save(project));
        return result;
    }

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<ProjectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Projects");
        User user = userRepository.findOneByLogin(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        Page<ProjectDTO> result = null;
        if (user.getAuthorities().contains(authorityRepository.findOne(AuthoritiesConstants.ADMIN))) {
            result = projectRepository.findAll(pageable).map(ProjectDTO::new);
        } else {
            Department department = user.getDepartment();
            if (department != null) {
                result = projectRepository.findAllByDepartment(pageable, department).map(ProjectDTO::new);
            } else {
                result = new PageImpl<>(Collections.emptyList());
            }
        }
        return result;
    }

    /**
     *  Get one project by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public ProjectDTO findOne(Long id) {
        log.debug("Request to get Project : {}", id);
        ProjectDTO project = new ProjectDTO(projectRepository.findOne(id));
        return project;
    }

    /**
     *  Delete the  project by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Project : {}", id);
        projectRepository.delete(id);
    }
}

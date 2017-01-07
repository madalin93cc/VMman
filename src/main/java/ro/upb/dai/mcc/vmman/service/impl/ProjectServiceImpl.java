package ro.upb.dai.mcc.vmman.service.impl;

import ro.upb.dai.mcc.vmman.service.ProjectService;
import ro.upb.dai.mcc.vmman.domain.Project;
import ro.upb.dai.mcc.vmman.repository.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ro.upb.dai.mcc.vmman.service.dto.ProjectDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Project.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{

    private final Logger log = LoggerFactory.getLogger(ProjectServiceImpl.class);

    @Inject
    private ProjectRepository projectRepository;

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
        Page<ProjectDTO> result = projectRepository.findAll(pageable).map(ProjectDTO::new);
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

package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.upb.dai.mcc.vmman.service.dto.ProjectDTO;

import java.util.List;

/**
 * Service Interface for managing Project.
 */
public interface ProjectService {

    /**
     * Save a project.
     *
     * @param project the entity to save
     * @return the persisted entity
     */
    ProjectDTO save(Project project);

    /**
     *  Get all the projects.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<ProjectDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" project.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    ProjectDTO findOne(Long id);

    /**
     *  Delete the "id" project.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

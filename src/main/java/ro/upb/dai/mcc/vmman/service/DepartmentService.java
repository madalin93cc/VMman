package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Department.
 */
public interface DepartmentService {

    /**
     * Save a department.
     *
     * @param department the entity to save
     * @return the persisted entity
     */
    Department save(Department department);

    /**
     *  Get all the departments.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Department> findAll(Pageable pageable);

    /**
     *  Get the "id" department.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Department findOne(Long id);

    /**
     *  Delete the "id" department.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}

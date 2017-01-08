package ro.upb.dai.mcc.vmman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.Project;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Project entity.
 */
@SuppressWarnings("unused")
public interface ProjectRepository extends JpaRepository<Project,Long> {
    Page<Project> findAllByDepartment(Pageable pageable, Department department);
}

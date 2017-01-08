package ro.upb.dai.mcc.vmman.repository;

import ro.upb.dai.mcc.vmman.domain.Department;

import org.springframework.data.jpa.repository.*;
import ro.upb.dai.mcc.vmman.domain.User;

import java.util.List;

/**
 * Spring Data JPA repository for the Department entity.
 */
@SuppressWarnings("unused")
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    Department findOneByManagerId(Long id);
}

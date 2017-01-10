package ro.upb.dai.mcc.vmman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.VmRequest;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VmRequest entity.
 */
@SuppressWarnings("unused")
public interface VmRequestRepository extends JpaRepository<VmRequest,Long> {

    @Query("select vmRequest from VmRequest vmRequest where vmRequest.from.login = ?#{principal.username}")
    List<VmRequest> findByFromIsCurrentUser();

    @Query("select vmRequest from VmRequest vmRequest where vmRequest.to.login = ?#{principal.username}")
    List<VmRequest> findByToIsCurrentUser();

    Page<VmRequest> findAllByFromDepartment(Pageable pageable, Department department);

    Integer countByOperatingSystemId(Long id);
}

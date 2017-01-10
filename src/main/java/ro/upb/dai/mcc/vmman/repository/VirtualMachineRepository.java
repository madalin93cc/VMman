package ro.upb.dai.mcc.vmman.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.VirtualMachine;

/**
 * Spring Data JPA repository for the VirtualMachine entity.
 */
@SuppressWarnings("unused")
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine,Long> {
    Page<VirtualMachine> findAllByProjectDepartment(Pageable pageable, Department department);

    Integer countByOperatingSystemId(Long id);
}

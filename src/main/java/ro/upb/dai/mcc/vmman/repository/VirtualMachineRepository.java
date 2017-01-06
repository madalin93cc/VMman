package ro.upb.dai.mcc.vmman.repository;

import ro.upb.dai.mcc.vmman.domain.VirtualMachine;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VirtualMachine entity.
 */
@SuppressWarnings("unused")
public interface VirtualMachineRepository extends JpaRepository<VirtualMachine,Long> {

}

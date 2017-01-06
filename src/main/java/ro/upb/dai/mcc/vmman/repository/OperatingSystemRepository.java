package ro.upb.dai.mcc.vmman.repository;

import ro.upb.dai.mcc.vmman.domain.OperatingSystem;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OperatingSystem entity.
 */
@SuppressWarnings("unused")
public interface OperatingSystemRepository extends JpaRepository<OperatingSystem,Long> {

}

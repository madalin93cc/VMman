package ro.upb.dai.mcc.vmman.repository;

import ro.upb.dai.mcc.vmman.domain.GenericVm;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GenericVm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GenericVmRepository extends JpaRepository<GenericVm, Long> {

}

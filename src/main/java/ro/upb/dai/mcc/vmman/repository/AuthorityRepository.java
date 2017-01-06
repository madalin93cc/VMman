package ro.upb.dai.mcc.vmman.repository;

import ro.upb.dai.mcc.vmman.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}

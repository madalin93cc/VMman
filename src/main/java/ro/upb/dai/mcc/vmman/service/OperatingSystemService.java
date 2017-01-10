package ro.upb.dai.mcc.vmman.service;

import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing OperatingSystem.
 */
public interface OperatingSystemService {

    /**
     * Save a operatingSystem.
     *
     * @param operatingSystem the entity to save
     * @return the persisted entity
     */
    OperatingSystem save(OperatingSystem operatingSystem);

    /**
     *  Get all the operatingSystems.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<OperatingSystem> findAll(Pageable pageable);

    /**
     *  Get the "id" operatingSystem.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    OperatingSystem findOne(Long id);

    /**
     *  Delete the "id" operatingSystem.
     *
     *  @param id the id of the entity
     */
    boolean delete(Long id);
}

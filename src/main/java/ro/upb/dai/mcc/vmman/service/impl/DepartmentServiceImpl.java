package ro.upb.dai.mcc.vmman.service.impl;

import ro.upb.dai.mcc.vmman.service.DepartmentService;
import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ro.upb.dai.mcc.vmman.service.dto.DepartmentDTO;

import javax.inject.Inject;
import java.util.List;

/**
 * Service Implementation for managing Department.
 */
@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService{

    private final Logger log = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Inject
    private DepartmentRepository departmentRepository;

    /**
     * Save a department.
     *
     * @param department the entity to save
     * @return the persisted entity
     */
    public DepartmentDTO save(Department department) {
        log.debug("Request to save Department : {}", department);
        DepartmentDTO result = new DepartmentDTO(departmentRepository.save(department));
        return result;
    }

    /**
     *  Get all the departments.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<DepartmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Departments");
        Page<DepartmentDTO> result = departmentRepository.findAll(pageable).map(DepartmentDTO::new);
        return result;
    }

    /**
     *  Get one department by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public DepartmentDTO findOne(Long id) {
        log.debug("Request to get Department : {}", id);
        DepartmentDTO department = new DepartmentDTO(departmentRepository.findOne(id));
        return department;
    }

    /**
     *  Delete the  department by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Department : {}", id);
        departmentRepository.delete(id);
    }
}

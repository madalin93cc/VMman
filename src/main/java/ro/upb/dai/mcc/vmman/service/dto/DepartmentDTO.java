package ro.upb.dai.mcc.vmman.service.dto;

import ro.upb.dai.mcc.vmman.domain.Department;
import ro.upb.dai.mcc.vmman.domain.User;
import ro.upb.dai.mcc.vmman.web.rest.vm.ManagedUserVM;

/**
 * Created by Madalin on 07/01/2017.
 */
public class DepartmentDTO {
    private Long id;
    private String name;
    private ManagedUserVM manager;

    public DepartmentDTO() {
    }

    public DepartmentDTO(Long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.manager = new ManagedUserVM(user);
    }

    public DepartmentDTO(Department department) {
        if (department != null) {
            this.id = department.getId();
            this.name = department.getName();
            User manager = department.getManager();
            if (manager != null) {
                this.manager = new ManagedUserVM(manager.getId(), manager.getLogin(), null, manager.getFirstName(), manager.getLastName(),
                    manager.getEmail(), manager.getActivated(), null, null, null, null, null, null, null);
            } else {
                this.manager = null;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ManagedUserVM getManager() {
        return manager;
    }

    public void setManager(ManagedUserVM manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "DepartmentDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }
}


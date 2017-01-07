package ro.upb.dai.mcc.vmman.service.dto;

import ro.upb.dai.mcc.vmman.domain.Project;

/**
 * Created by Madalin on 07/01/2017.
 */
public class ProjectDTO {
    private Long id;
    private String name;
    private DepartmentDTO department;

    public ProjectDTO() {
    }

    public ProjectDTO(Project project) {
        if (project != null) {
            this.id = project.getId();
            this.name = project.getName();
            this.department = new DepartmentDTO(project.getDepartment());
        }
    }

    public ProjectDTO(Long id, String name, DepartmentDTO department) {
        this.id = id;
        this.name = name;
        this.department = department;
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

    public DepartmentDTO getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentDTO department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "ProjectDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", department=" + department +
            '}';
    }
}

package ro.upb.dai.mcc.vmman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "project")
    @JsonIgnore
    private Set<VirtualMachine> virtualMachines = new HashSet<>();

    @ManyToOne
    private Department department;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public Project virtualMachines(Set<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
        return this;
    }

    public Project addVirtualMachines(VirtualMachine virtualMachine) {
        virtualMachines.add(virtualMachine);
        virtualMachine.setProject(this);
        return this;
    }

    public Project removeVirtualMachines(VirtualMachine virtualMachine) {
        virtualMachines.remove(virtualMachine);
        virtualMachine.setProject(null);
        return this;
    }

    public void setVirtualMachines(Set<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }


    public Department getDepartment() {
        return department;
    }

    public Project project(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Project{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}

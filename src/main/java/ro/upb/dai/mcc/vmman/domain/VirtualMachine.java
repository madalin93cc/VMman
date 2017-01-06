package ro.upb.dai.mcc.vmman.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import ro.upb.dai.mcc.vmman.domain.enumeration.Environment;

/**
 * A VirtualMachine.
 */
@Entity
@Table(name = "virtual_machine")
public class VirtualMachine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment")
    private Environment environment;

    @Column(name = "ip")
    private String ip;

    @Column(name = "hdd")
    private String hdd;

    @Column(name = "processor")
    private String processor;

    @Column(name = "ram")
    private String ram;

    @ManyToOne
    private Project project;

    @ManyToOne
    private OperatingSystem operatingSystem;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public VirtualMachine name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public VirtualMachine description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public VirtualMachine environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getIp() {
        return ip;
    }

    public VirtualMachine ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHdd() {
        return hdd;
    }

    public VirtualMachine hdd(String hdd) {
        this.hdd = hdd;
        return this;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getProcessor() {
        return processor;
    }

    public VirtualMachine processor(String processor) {
        this.processor = processor;
        return this;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public VirtualMachine ram(String ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Project getProject() {
        return project;
    }

    public VirtualMachine project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public VirtualMachine operatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VirtualMachine virtualMachine = (VirtualMachine) o;
        if (virtualMachine.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, virtualMachine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VirtualMachine{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", description='" + description + "'" +
            ", environment='" + environment + "'" +
            ", ip='" + ip + "'" +
            ", hdd='" + hdd + "'" +
            ", processor='" + processor + "'" +
            ", ram='" + ram + "'" +
            '}';
    }
}

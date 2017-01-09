package ro.upb.dai.mcc.vmman.domain;


import ro.upb.dai.mcc.vmman.domain.enumeration.Environment;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A VmRequest.
 */
@Entity
@Table(name = "vm_request")
public class VmRequest extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "hdd", nullable = false)
    private String hdd;

    @NotNull
    @Column(name = "processor", nullable = false)
    private String processor;

    @NotNull
    @Column(name = "ram", nullable = false)
    private String ram;

    @Column(name = "approved")
    private Boolean approved;

    @Column(name = "created")
    private Boolean created;

    @ManyToOne
    private OperatingSystem operatingSystem;

    @ManyToOne
    private User from;

    @ManyToOne
    private User to;

    @ManyToOne
    private Project project;

    @Enumerated(EnumType.STRING)
    @Column(name = "environment")
    private Environment environment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public VmRequest name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public VmRequest description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHdd() {
        return hdd;
    }

    public VmRequest hdd(String hdd) {
        this.hdd = hdd;
        return this;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getProcessor() {
        return processor;
    }

    public VmRequest processor(String processor) {
        this.processor = processor;
        return this;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public VmRequest ram(String ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Boolean isApproved() {
        return approved;
    }

    public VmRequest approved(Boolean approved) {
        this.approved = approved;
        return this;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getCreated() {
        return created;
    }

    public VmRequest created(Boolean created) {
        this.created = created;
        return this;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public VmRequest operatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public User getFrom() {
        return from;
    }

    public VmRequest from(User user) {
        this.from = user;
        return this;
    }

    public void setFrom(User user) {
        this.from = user;
    }

    public User getTo() {
        return to;
    }

    public VmRequest to(User user) {
        this.to = user;
        return this;
    }

    public void setTo(User user) {
        this.to = user;
    }

    public Project getProject() {
        return project;
    }

    public VmRequest project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public VmRequest environment(Environment environment) {
        this.environment = environment;
        return this;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VmRequest vmRequest = (VmRequest) o;
        if (vmRequest.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, vmRequest.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "VmRequest{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", hdd='" + hdd + '\'' +
            ", processor='" + processor + '\'' +
            ", ram='" + ram + '\'' +
            ", approved=" + approved +
            ", created=" + created +
            ", operatingSystem=" + operatingSystem +
            ", from=" + from +
            ", to=" + to +
            ", project=" + project +
            ", environment=" + environment +
            '}';
    }
}

package ro.upb.dai.mcc.vmman.service.dto;

import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import ro.upb.dai.mcc.vmman.domain.VmRequest;
import ro.upb.dai.mcc.vmman.domain.enumeration.Environment;

/**
 * Created by Madalin on 08/01/2017.
 */
public class VmRequestDTO {
    private Long id;
    private String name;
    private String description;
    private String hdd;
    private String processor;
    private String ram;
    private Boolean approved;
    private Boolean created;
    private OperatingSystem operatingSystem;
    private ManagedUserDTO from;
    private ManagedUserDTO to;
    private ProjectDTO project;
    private Environment environment;

    public VmRequestDTO() {
    }

    public VmRequestDTO (VmRequest vmRequest) {
        this.id = vmRequest.getId();
        this.name = vmRequest.getName();
        this.description = vmRequest.getDescription();
        this.hdd = vmRequest.getHdd();
        this.processor = vmRequest.getProcessor();
        this.ram = vmRequest.getRam();
        this.approved = vmRequest.isApproved();
        this.created = vmRequest.getCreated();
        this.operatingSystem = vmRequest.getOperatingSystem();
        this.from = new ManagedUserDTO(vmRequest.getFrom());
        this.to = (vmRequest.getTo() != null)? new ManagedUserDTO(vmRequest.getTo()): null;
        this.project = (vmRequest.getProject() != null)? new ProjectDTO(vmRequest.getProject()): null;
        this.environment = vmRequest.getEnvironment();
    }

    public VmRequestDTO(Long id, String name, String description, String hdd, String processor, String ram, Boolean approved, Boolean created, OperatingSystem operatingSystem, ManagedUserDTO from, ManagedUserDTO to, ProjectDTO project, Environment environment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hdd = hdd;
        this.processor = processor;
        this.ram = ram;
        this.approved = approved;
        this.created = created;
        this.operatingSystem = operatingSystem;
        this.from = from;
        this.to = to;
        this.project = project;
        this.environment = environment;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHdd() {
        return hdd;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getCreated() {
        return created;
    }

    public void setCreated(Boolean created) {
        this.created = created;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public ManagedUserDTO getFrom() {
        return from;
    }

    public void setFrom(ManagedUserDTO from) {
        this.from = from;
    }

    public ManagedUserDTO getTo() {
        return to;
    }

    public void setTo(ManagedUserDTO to) {
        this.to = to;
    }

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String toString() {
        return "VmRequestDTO{" +
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

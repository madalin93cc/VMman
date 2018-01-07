package ro.upb.dai.mcc.vmman.service.dto;

import ro.upb.dai.mcc.vmman.domain.OperatingSystem;
import ro.upb.dai.mcc.vmman.domain.VirtualMachine;
import ro.upb.dai.mcc.vmman.domain.enumeration.Environment;

import java.io.Serializable;

/**
 * Created by Madalin on 07/01/2017.
 */
public class VirtualMachineDTO implements Serializable {
    private Long id;
    private String name;
    private String description;
    private Environment environment;
    private String ip;
    private String hdd;
    private String processor;
    private String ram;
    private ProjectDTO project;
    private OperatingSystem operatingSystem;
    private GenericVmDTO genericVm;

    public VirtualMachineDTO() {
    }

    public VirtualMachineDTO(VirtualMachine virtualMachine) {
        this.id = virtualMachine.getId();
        this.name = virtualMachine.getName();
        this.description = virtualMachine.getDescription();
        this.environment = virtualMachine.getEnvironment();
        this.ip = virtualMachine.getIp();
        this.hdd = virtualMachine.getHdd();
        this.processor = virtualMachine.getProcessor();
        this.ram = virtualMachine.getRam();
        if (virtualMachine.getProject() != null) {
            this.project = new ProjectDTO(virtualMachine.getProject().getId(), virtualMachine.getProject().getName(), null);
        }
        if (virtualMachine.getGenericVm() != null) {
            this.genericVm = new GenericVmDTO(virtualMachine.getGenericVm());
        }
        this.operatingSystem = virtualMachine.getOperatingSystem();
    }

    public VirtualMachineDTO(Long id, String name, String description, Environment environment, String ip, String hdd, String processor, String ram, ProjectDTO project, OperatingSystem operatingSystem, GenericVmDTO genericVmDTO) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.environment = environment;
        this.ip = ip;
        this.hdd = hdd;
        this.processor = processor;
        this.ram = ram;
        this.project = project;
        this.operatingSystem = operatingSystem;
        this.genericVm = genericVmDTO;
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

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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

    public ProjectDTO getProject() {
        return project;
    }

    public void setProject(ProjectDTO project) {
        this.project = project;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public GenericVmDTO getGenericVm() {
        return genericVm;
    }

    public void setGenericVm(GenericVmDTO genericVm) {
        this.genericVm = genericVm;
    }

    @Override
    public String toString() {
        return "VirtualMachineDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", environment=" + environment +
            ", ip='" + ip + '\'' +
            ", hdd='" + hdd + '\'' +
            ", processor='" + processor + '\'' +
            ", ram='" + ram + '\'' +
            ", project=" + project +
            ", operatingSystem=" + operatingSystem +
            '}';
    }
}

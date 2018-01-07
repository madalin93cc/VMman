package ro.upb.dai.mcc.vmman.service.dto;

import ro.upb.dai.mcc.vmman.domain.GenericVm;
import java.io.Serializable;

public class GenericVmDTO implements Serializable {
    private Long id;
    private String name;
    private String hdd;
    private String processor;
    private String ram;

    public GenericVmDTO() {
    }

    public GenericVmDTO(GenericVm genericVm) {
        this.id = genericVm.getId();
        this.name = genericVm.getName();
        this.hdd = genericVm.getHdd();
        this.processor = genericVm.getProcessor();
        this.ram = genericVm.getRam();
    }

    public GenericVmDTO(Long id, String name, String hdd, String processor, String ram) {
        this.id = id;
        this.name = name;
        this.hdd = hdd;
        this.processor = processor;
        this.ram = ram;
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

    @Override
    public String toString() {
        return "GenericVmDTO{" +
            "id=" + id +
            ", hdd='" + hdd + '\'' +
            ", processor='" + processor + '\'' +
            ", ram='" + ram + '\'' +
            '}';
    }
}

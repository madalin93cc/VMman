package ro.upb.dai.mcc.vmman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A GenericVm.
 */
@Entity
@Table(name = "generic_vm")
public class GenericVm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "hdd")
    private String hdd;

    @Column(name = "processor")
    private String processor;

    @Column(name = "ram")
    private String ram;

    @OneToMany(mappedBy = "genericVm")
    @JsonIgnore
    private Set<VirtualMachine> virtualMachines = new HashSet<>();

    @OneToMany(mappedBy = "genericVm")
    @JsonIgnore
    private Set<VmRequest> vmRequests = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
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

    public GenericVm hdd(String hdd) {
        this.hdd = hdd;
        return this;
    }

    public void setHdd(String hdd) {
        this.hdd = hdd;
    }

    public String getProcessor() {
        return processor;
    }

    public GenericVm processor(String processor) {
        this.processor = processor;
        return this;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public GenericVm ram(String ram) {
        this.ram = ram;
        return this;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public Set<VirtualMachine> getVirtualMachines() {
        return virtualMachines;
    }

    public GenericVm virtualMachines(Set<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
        return this;
    }

    public GenericVm addVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachines.add(virtualMachine);
        virtualMachine.setGenericVm(this);
        return this;
    }

    public GenericVm removeVirtualMachine(VirtualMachine virtualMachine) {
        this.virtualMachines.remove(virtualMachine);
        virtualMachine.setGenericVm(null);
        return this;
    }

    public void setVirtualMachines(Set<VirtualMachine> virtualMachines) {
        this.virtualMachines = virtualMachines;
    }

    public Set<VmRequest> getVmRequests() {
        return vmRequests;
    }

    public GenericVm vmRequests(Set<VmRequest> vmRequests) {
        this.vmRequests = vmRequests;
        return this;
    }

    public GenericVm addVmRequest(VmRequest vmRequest) {
        this.vmRequests.add(vmRequest);
        vmRequest.setGenericVm(this);
        return this;
    }

    public GenericVm removeVmRequest(VmRequest vmRequest) {
        this.vmRequests.remove(vmRequest);
        vmRequest.setGenericVm(null);
        return this;
    }

    public void setVmRequests(Set<VmRequest> vmRequests) {
        this.vmRequests = vmRequests;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenericVm genericVm = (GenericVm) o;
        if (genericVm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), genericVm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GenericVm{" +
            "id=" + getId() +
            ", hdd='" + getHdd() + "'" +
            ", processor='" + getProcessor() + "'" +
            ", ram='" + getRam() + "'" +
            "}";
    }
}

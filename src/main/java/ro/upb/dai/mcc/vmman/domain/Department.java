package ro.upb.dai.mcc.vmman.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Department.
 */
@Entity
@Table(name = "department")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<User> managers = new HashSet<>();

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private Set<User> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public Department managers(Set<User> users) {
        this.managers = users;
        return this;
    }

    public Department addManagers(User user) {
        managers.add(user);
        user.setDepartment(this);
        return this;
    }

    public Department removeManagers(User user) {
        managers.remove(user);
        user.setDepartment(null);
        return this;
    }

    public void setManagers(Set<User> users) {
        this.managers = users;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Department users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Department addUsers(User user) {
        users.add(user);
        user.setDepartment(this);
        return this;
    }

    public Department removeUsers(User user) {
        users.remove(user);
        user.setDepartment(null);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Department department = (Department) o;
        if (department.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, department.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Department{" +
            "id=" + id +
            ", name='" + name + "'" +
            '}';
    }
}

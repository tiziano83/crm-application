package com.devtb.crmapp.domain;

import javax.persistence.*;
import java.util.List;

@Entity(name="users")
public class User extends BaseEntity {

    private String userName;
    private String password;
    private String fullName;
    private List<Role> roles;

    @Id
    @Column(name = "userId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        super.id=id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },
            fetch = FetchType.EAGER,
            targetEntity = Role.class)
    @JoinTable(name = "User_Role", joinColumns = {
            @JoinColumn(name = "userId", referencedColumnName = "userId")},
            inverseJoinColumns = {@JoinColumn(name = "roleId", referencedColumnName = "roleId")}
    )
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }
}

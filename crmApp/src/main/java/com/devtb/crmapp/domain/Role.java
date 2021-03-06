package com.devtb.crmapp.domain;

import com.devtb.crmapp.service.DTOS.RoleDTO;

import javax.persistence.*;
import java.util.Collection;

@Entity(name = "roles")
public class Role extends BaseEntity {

    private String roleName;

    @ManyToMany(mappedBy = "roles")
    private Collection<User> appUserList;

    @Id
    @Column(name = "roleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        super.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleDTO toRoleDTO() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleId(getId());
        roleDTO.setRoleName(getRoleName());
        return roleDTO;
    }

}

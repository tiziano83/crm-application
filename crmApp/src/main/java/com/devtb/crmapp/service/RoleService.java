package com.devtb.crmapp.service;

import com.devtb.crmapp.domain.Role;
import com.devtb.crmapp.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void createDefaultRoles() throws Exception {
        try {
            if (roleRepository.findByRoleName("ADMIN").isEmpty())
                roleRepository.save(createRole(("ADMIN")));
            if (roleRepository.findByRoleName("USER").isEmpty())
                roleRepository.save(createRole(("USER")));
        } catch (Exception ex){
            throw new Exception(String.format("Error on creation of default roles - %s ",ex.getMessage()));
        }
    }

    private Role createRole(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }

    public Optional<Role> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }
}

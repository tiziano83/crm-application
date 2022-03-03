package com.devtb.crmapp.service;

import com.devtb.crmapp.domain.Role;
import com.devtb.crmapp.domain.User;
import com.devtb.crmapp.repository.RoleRepository;
import com.devtb.crmapp.service.DTOS.RoleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        } catch (Exception ex) {
            throw new Exception(String.format("Error on creation of default roles - %s ", ex.getMessage()));
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

    public List<Role> getValidRoles(List<Long> roleIds) throws Exception {
        List<Role> roles = new ArrayList<>();
        for (Long roleId : roleIds) {
            Optional<Role> role = roleRepository.findById(roleId);
            if (role.isEmpty())
                throw new Exception(String.format("role with id %s not found", roleId));
            roles.add(role.get());
        }
        return roles;
    }

    public List<RoleDTO> getAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(Role::toRoleDTO)
                .collect(Collectors.toList());
    }

    public RoleDTO getRoleById(Long roleId) throws Exception {
        return findRoleById(roleId).get().toRoleDTO();
    }

    private Optional<Role> findRoleById(Long roleId) throws Exception {
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isEmpty())
            throw new Exception("Role not found");
        return role;
    }

    public void createNewRole(RoleDTO roleDTO) throws Exception {
        validate(roleDTO, true);
        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        roleRepository.save(role);
    }

    public void updateRole(RoleDTO roleDTO) throws Exception {
        validate(roleDTO,false);
        Optional<Role> role = findRoleById(roleDTO.getRoleId());
        Role r = role.get();
        r.setRoleName(roleDTO.getRoleName());
        roleRepository.save(r);
    }

    private void validate(RoleDTO roleDTO, boolean creation) throws Exception {
        if (roleDTO.getRoleName() == null || roleDTO.getRoleName().trim().isEmpty())
            throw new Exception("Role name is required");
        if(creation && roleAlreadyExist(roleDTO.getRoleName()))
            throw new Exception("this role already exists");
        if (!creation && roleDTO.getRoleId() == null)
        throw new Exception("RoleId is required");
    }

    private boolean roleAlreadyExist(String roleName) {
        return roleRepository.countByRoleName(roleName)>0;
    }

    public void deleteRole(Long roleId) throws Exception {
        findRoleById(roleId);
        roleRepository.deleteById(roleId);
    }
}

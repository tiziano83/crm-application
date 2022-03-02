package com.devtb.crmapp.service;


import com.devtb.crmapp.domain.Role;
import com.devtb.crmapp.domain.User;
import com.devtb.crmapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;
    private RoleService roleService;

    public UserService(UserRepository userRepository,RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    public void createDefaultUser() throws Exception {
        try {
            if (userRepository.findByUserName("admin").isEmpty()) {
                Optional<Role> roles = roleService.findByRoleName("ADMIN");
                User appUser = createUser(List.of(roles.get()),"admin","admin","$2a$10$IqTJTjn39IU5.7sSCDQxzu3xug6z/LPU6IF0azE/8CkHCwYEnwBX.");
                userRepository.save(appUser);
            }
            if (userRepository.findByUserName("user").isEmpty()) {
                Optional<Role> roles = roleService.findByRoleName("USER");

                User appUser = createUser(List.of(roles.get()),"user","user","$2a$12$LUxev85LH3TjBDBCmuKPn.NlFZYbTLOKOttb7RNCUOkoF4/ujFPmS");
                userRepository.save(appUser);
            }
        } catch (Exception e) {
            throw new Exception(String.format("Error on creation of default users - % s", e.getMessage()));
        }
    }

    public User createUser(List<Role> rolesList,String username,String fullName,String password) {
        User appUser = new User();
        appUser.setUserName(username);
        appUser.setFullName(fullName);
        appUser.setPassword(password);
        appUser.setRoles(rolesList);
        return appUser;
    }
}

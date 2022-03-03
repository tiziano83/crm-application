package com.devtb.crmapp;


import com.devtb.crmapp.service.RoleService;
import com.devtb.crmapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


/**
 * Class for managing startup operations
 */
@Component
public class StartupOperations {
    private final static Logger log = LoggerFactory.getLogger(StartupOperations.class);

    private RoleService roleService;
    private UserService userService;

    public StartupOperations(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    public void executeStartupOperations() {

        try {
            roleService.createDefaultRoles();
            userService.createDefaultUsers();

        } catch (Exception e) {
            e.printStackTrace();
            log.error(String.format("error on startup operations - %s", e.getMessage()));
        }
    }

}

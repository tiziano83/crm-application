package com.devtb.crmapp.controller;

import com.devtb.crmapp.service.DTOS.RoleDTO;
import com.devtb.crmapp.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class RoleController extends AbstractController {

    private final static Logger log = LoggerFactory.getLogger(RoleController.class);

    private final RoleService roleService;
    private static final String mapping = "/role";

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(mapping + "/all")
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(mapping + "/id/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable("roleId") Long roleId) {
        try {
            return ResponseEntity.ok(roleService.getRoleById(roleId));
        } catch (Exception e) {
            log.error("error on getRoleById - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found", e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(mapping)
    public ResponseEntity<String> createNewRole(@RequestBody RoleDTO roleDTO) {
        try {
            roleService.createNewRole(roleDTO);
            return ResponseEntity.ok("role created");
        } catch (Exception e) {
            log.error("error on createNewRole - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(mapping)
    public ResponseEntity<String> updateRole(@RequestBody RoleDTO roleDTO) {
        try {
            roleService.updateRole(roleDTO);
            return ResponseEntity.ok("role updated");
        } catch (Exception e) {
            log.error("error on updateRole - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(mapping+"/{roleId}")
    public ResponseEntity<String> deleteRole(@PathVariable("roleId") Long roleId) {
        try {
            roleService.deleteRole(roleId);
            return ResponseEntity.ok("role deleted");
        } catch (Exception e) {
            log.error("error on deleteRole - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

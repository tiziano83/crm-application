package com.devtb.crmapp.controller;

import com.devtb.crmapp.service.DTOS.UserRequestDTO;
import com.devtb.crmapp.service.DTOS.UserResponseDTO;
import com.devtb.crmapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class UserController extends AbstractController{

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private static final String mapping = "/user";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(mapping + "/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(mapping + "/id/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable("userId") Long userId) {
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (Exception e) {
            log.error("error on getUserById - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(mapping)
    public ResponseEntity<String> createNewUser(@RequestBody UserRequestDTO userDTO) {
        try {
            userService.createNewUser(userDTO);
            return ResponseEntity.ok("user created");
        } catch (Exception e) {
            log.error("error on createNewUser - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PutMapping(mapping)
    public ResponseEntity<String> updateUser(@RequestBody UserRequestDTO userDTO) {
        try {
            userService.updateUser(userDTO);
            return ResponseEntity.ok("user updated");
        } catch (Exception e) {
            log.error("error on updateUser - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @DeleteMapping(mapping+"/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("user deleted");
        } catch (Exception e) {
            log.error("error on deleteUSer - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}

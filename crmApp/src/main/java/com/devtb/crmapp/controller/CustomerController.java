package com.devtb.crmapp.controller;

import com.devtb.crmapp.service.DTOS.CustomerRequestDTO;
import com.devtb.crmapp.domain.Customer;
import com.devtb.crmapp.service.CustomerService;
import com.devtb.crmapp.service.DTOS.CustomerResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CustomerController extends AbstractController {
    private final static Logger log = LoggerFactory.getLogger(CustomerController.class);


    private CustomerService customerService;
    private static final String mapping = "/customer";

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(mapping + "/all")
    public ResponseEntity<List<CustomerRequestDTO>> getAllCustomer() {
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(mapping + "/id/{customerId}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable("customerId") Long customerId) {
        try {
            return ResponseEntity.ok(customerService.findById(customerId));
        } catch (Exception e) {
            log.error("error on getCustomerById - {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found", e);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping(mapping)
    public ResponseEntity<String> createNewCustomer(@RequestPart("CustomerDTO") CustomerRequestDTO customerDTO, @RequestPart("photo") MultipartFile photo) {
        try {
            customerService.createNewCustomer(customerDTO, photo);
            return ResponseEntity.ok("user created");
        } catch (Exception e) {
            log.error("error on createNewCustomer - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(mapping)
    public ResponseEntity<String> updateCustomer(@RequestPart("CustomerDTO") CustomerRequestDTO customerDTO) {
        try {
            customerService.updateCustomer(customerDTO);
            return ResponseEntity.ok("user updated");
        } catch (Exception e) {
            log.error("error on updateCustomer - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PutMapping(mapping+"/photo")
    public ResponseEntity<String> updateCustomerWithPhoto(@RequestPart("CustomerDTO") CustomerRequestDTO customerDTO, @RequestPart("photo") MultipartFile photo) {
        try {
            customerService.updateCustomerWithPhoto(customerDTO, photo);
            return ResponseEntity.ok("user updated");
        } catch (Exception e) {
            log.error("error on updateCustomerWithPhoto - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @DeleteMapping(mapping+"/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") Long customerId) {
        try {
            customerService.deleteCustomer(customerId);
            return ResponseEntity.ok("user deleted");
        } catch (Exception e) {
            log.error("error on deleteCustomer - {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}

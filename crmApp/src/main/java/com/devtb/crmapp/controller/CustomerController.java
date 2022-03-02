package com.devtb.crmapp.controller;


import com.devtb.crmapp.domain.Customer;
import com.devtb.crmapp.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CustomerController extends AbstractController {

    private CustomerService customerService;
    private static final String mapping = "/customer";

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(mapping+"/all")
    public ResponseEntity<List<Customer>> getAllCustomer() {
       return (ResponseEntity<List<Customer>>) ResponseEntity.ok(customerService.getAllCustomer());
    }
}

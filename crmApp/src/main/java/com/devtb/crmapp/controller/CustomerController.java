package com.devtb.crmapp.controller;


import com.devtb.crmapp.domain.Customer;
import com.devtb.crmapp.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<List<Customer>> getAllCustomer(HttpServletRequest httpServletRequest) {
       return  ResponseEntity.ok(customerService.getAllCustomer());
    }
}

package com.devtb.crmapp.service;

import com.devtb.crmapp.domain.Customer;
import com.devtb.crmapp.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomer(){
        return customerRepository.findAll();
    }

}

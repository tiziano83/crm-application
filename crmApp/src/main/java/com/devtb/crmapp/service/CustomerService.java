package com.devtb.crmapp.service;

import com.devtb.crmapp.domain.Customer;
import com.devtb.crmapp.domain.User;
import com.devtb.crmapp.repository.CustomerRepository;
import com.devtb.crmapp.service.DTOS.CustomerRequestDTO;
import com.devtb.crmapp.service.DTOS.CustomerResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {

    private CustomerRepository customerRepository;
    private UserService userService;

    public CustomerService(CustomerRepository customerRepository, UserService userService) {
        this.customerRepository = customerRepository;
        this.userService = userService;
    }

    public List<CustomerRequestDTO> getAllCustomer() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(Customer::toCustomerRequestDTO).collect(Collectors.toList());
    }

    public CustomerResponseDTO findById(Long customerId) throws Exception {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent())
            return customer.get().toCustomerResponseDTO();
        throw new Exception("Customer not found");
    }

    public void createNewCustomer(CustomerRequestDTO customerDTO, MultipartFile photo) throws Exception {
        validate(customerDTO, photo, true);

        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setSurname(customerDTO.getSurname());
        customer.setProfileImage(photo.getBytes());

        Optional<User> user = userService.findById(customerDTO.getCreationUserId());
        if (user.isEmpty())
            throw new Exception("creation user not found");

        customer.setCreationUser(user.get());
        customer.setProfileImage(photo.getBytes());
        customerRepository.save(customer);

    }

    public void updateCustomer(CustomerRequestDTO customerDTO) throws Exception {
        validate(customerDTO, null, false);

        update(customerDTO, null);

    }

    public void updateCustomerWithPhoto(CustomerRequestDTO customerDTO, MultipartFile photo) throws Exception {
        validate(customerDTO, photo, false);

        update(customerDTO, photo);
    }


    private void validate(CustomerRequestDTO customerDTO, MultipartFile photo, boolean creation) throws Exception {

        if (customerDTO.getName() == null || customerDTO.getName().trim().isEmpty())
            throw new Exception("Name is required");

        if (customerDTO.getSurname() == null || customerDTO.getSurname().trim().isEmpty())
            throw new Exception("Surname is required");

        if (creation && customerDTO.getCreationUserId() == null)
            throw new Exception("Creation user's id is required");

        if (creation && photo.isEmpty())
            throw new Exception("profile image is required");

        if (!creation && customerDTO.getUpdateUserId() == null)
            throw new Exception("Update user's id is required");

        if (!creation && customerDTO.getCustomerID() == null)
            throw new Exception("Customer id is required");

    }

    private Optional<Customer> findCustomerById(Long customerId) throws Exception {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isEmpty())
            throw new Exception("Customer not found");
        return customer;
    }

    public void deleteCustomer(Long customerId) throws Exception {
        customerRepository.deleteById(customerId);
    }


    private void update(CustomerRequestDTO customerDTO, MultipartFile photo) throws Exception {
        Optional<Customer> customer = findCustomerById(customerDTO.getCustomerID());

        Optional<User> user = userService.findById(customerDTO.getUpdateUserId());

        if (user.isEmpty())
            throw new Exception("Update user not found");

        Customer c = customer.get();
        c.setName(customerDTO.getName());
        c.setSurname(customerDTO.getSurname());
        c.setUpdateUser(user.get());

        if (photo!=null && !photo.isEmpty())
            c.setProfileImage(photo.getBytes());

        customerRepository.save(c);
    }

}

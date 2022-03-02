package com.devtb.crmapp.domain;

import com.devtb.crmapp.service.DTOS.CustomerRequestDTO;
import com.devtb.crmapp.service.DTOS.CustomerResponseDTO;

import javax.persistence.*;

@Entity(name = "customers")
public class Customer extends BaseEntity {

    private String name;
    private String surname;
    private User creationUser;
    private User updateUser;
    private byte[] profileImage;


    @Id
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        super.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Lob
    @Column(name = "profileImage", columnDefinition="BLOB")
    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] logo) {
        this.profileImage = logo;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createUser")
    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "updateUser")
    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User user) {
        this.updateUser = user;
    }

    public CustomerRequestDTO toCustomerRequestDTO() {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO();
        customerRequestDTO.setCustomerID(getId());
        customerRequestDTO.setName(getName());
        customerRequestDTO.setSurname(getSurname());
        if (getCreationUser() != null)
            customerRequestDTO.setCreationUserId(getCreationUser().getId());
        if (getUpdateUser() != null)
            customerRequestDTO.setUpdateUserId(getUpdateUser().getId());
        return customerRequestDTO;
    }

    public CustomerResponseDTO toCustomerResponseDTO() {
        CustomerResponseDTO customerResponseDTO = new CustomerResponseDTO();
        customerResponseDTO.setCreationUser(getCreationUser());
        customerResponseDTO.setUpdateUser(getUpdateUser());
        customerResponseDTO.setName(getName());
        customerResponseDTO.setSurname(getSurname());
        customerResponseDTO.setProfileImage(getProfileImage());
        return customerResponseDTO;
    }
}

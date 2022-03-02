package com.devtb.crmapp.domain;

import javax.persistence.*;

@Entity(name="customers")
public class Customer extends BaseEntity {

    private String name;
    private String surname;
    private User creationUser;
    private User updateUser;
    @Lob
    private byte[] profileImage;


    @Id
    @Column(name = "customerId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        super.id=id;
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

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] logo) {
        this.profileImage = logo;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createUser")
    public User getCreationUser() {
        return creationUser;
    }

    public void setCreationUser(User user) {
        this.creationUser = user;
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updateUser")
    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User user) {
        this.updateUser = user;
    }


}

package com.devtb.crmapp.auth;

import com.devtb.crmapp.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;


/**
 * Provides a basic implementation of the UserDetails interface
 */

public class CustomUserDetails implements UserDetails {

    public static final String ROLES_PREFIX = "ROLE_";

    private User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if (user.getRoles().isEmpty())
            return Collections.emptyList();

        return user.getRoles().stream().map(
                role -> (GrantedAuthority) () -> ROLES_PREFIX + role
        ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getFullName(){
        return user.getFullName();
    }



}

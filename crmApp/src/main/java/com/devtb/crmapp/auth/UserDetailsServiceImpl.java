package com.devtb.crmapp.auth;

import com.devtb.crmapp.domain.User;
import com.devtb.crmapp.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {

        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> applicationUser = userRepository.findByUserName(username);
        if (applicationUser.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new CustomUserDetails(applicationUser.get());

    }
}

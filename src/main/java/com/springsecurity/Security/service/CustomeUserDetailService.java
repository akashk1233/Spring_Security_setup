package com.springsecurity.Security.service;

import com.springsecurity.Security.model.CustomeUserDetails;
import com.springsecurity.Security.model.Users;
import com.springsecurity.Security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomeUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepo.findByUsername(username);
        if(users == null){
            throw new UsernameNotFoundException("User not found with the given name");
        }

        return new CustomeUserDetails(users);
    }
}

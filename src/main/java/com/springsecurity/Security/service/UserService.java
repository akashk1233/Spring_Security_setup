package com.springsecurity.Security.service;

import com.springsecurity.Security.model.UserRequest;
import com.springsecurity.Security.model.Users;
import com.springsecurity.Security.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.Serial;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private JwtService jwtService;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    AuthenticationManager authManager;

    public Users registerUser(UserRequest userRequest) throws Exception{
//        Users user1 = new Users();
         Optional<Users> user=userRepo.findById(userRequest.getId());
        if(user.isPresent()){
            throw new Exception("user already exist with given id"+userRequest.getId());
        }
       Users user1 = new Users(userRequest.getId(),userRequest.getUsername(),userRequest.getPassword());
        user1.setPassword(encoder.encode(user1.getPassword()));
        return userRepo.save(user1);
    }

    public String login(UserRequest user) throws Exception{
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        if (authentication.isAuthenticated())
            return jwtService.getToken(user.getUsername());
        return "fail";

    }

}

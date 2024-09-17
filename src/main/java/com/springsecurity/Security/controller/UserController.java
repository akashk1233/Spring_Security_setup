package com.springsecurity.Security.controller;

import ch.qos.logback.core.CoreConstants;
import com.springsecurity.Security.model.UserRequest;
import com.springsecurity.Security.model.Users;
import com.springsecurity.Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    public Users registerUser(@RequestBody UserRequest userRequest) throws Exception{

        return userService.registerUser(userRequest);
    }
    @PostMapping("/login")
    public String login(@RequestBody UserRequest user) throws Exception{
        System.out.println(user);
//        return "success";
        return userService.login(user);
    }
}

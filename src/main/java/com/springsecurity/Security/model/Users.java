package com.springsecurity.Security.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Entity
public class Users {

    @Id
    private int id;
    private String username;
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Users(int id, String userName, String password) {
        this.id = id;
        this.username = userName;
        this.password = password;
    }
    public Users() {

    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

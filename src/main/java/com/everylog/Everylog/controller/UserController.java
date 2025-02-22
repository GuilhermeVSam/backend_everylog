package com.everylog.Everylog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everylog.Everylog.dto.User;
import com.everylog.Everylog.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;

    public UserController(UserDetailsServiceImpl userDetailsServiceImpl) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUserInfo(@AuthenticationPrincipal Jwt jwt) {
        String username = jwt.getSubject();
        User user = userDetailsServiceImpl.loadUserDetailsByUsername(username);
        return ResponseEntity.ok(user);
    }
}
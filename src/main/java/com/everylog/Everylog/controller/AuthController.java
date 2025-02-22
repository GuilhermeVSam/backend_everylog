package com.everylog.Everylog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.everylog.Everylog.dto.LoginRequest;
import com.everylog.Everylog.dto.LoginResponse;
import com.everylog.Everylog.dto.SignupRequest;
import com.everylog.Everylog.service.AuthService;
import com.everylog.Everylog.service.UserDetailsServiceImpl;
import com.everylog.Everylog.utilities.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private final AuthService authService;

    @Autowired
    private final UserDetailsServiceImpl userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, UserDetailsServiceImpl userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        UserDetails userDetails = userService.loadUserByUsername(loginRequest.getUsername());

        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        userService.registerUser(signupRequest);
        return ResponseEntity.ok("User registered successfully");
    }
}

package com.everylog.Everylog.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.everylog.Everylog.dto.SignupRequest;
import com.everylog.Everylog.dto.User;
import com.everylog.Everylog.model.UserModel;
import com.everylog.Everylog.repository.UserRepository;
import com.everylog.Everylog.security.UserAuthenticated;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(UserAuthenticated::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User loadUserDetailsByUsername(String username) {
        Optional<UserModel> userDetails = userRepository.findByUsername(username);
        if (userDetails.isPresent()) {
            UserModel userModel = userDetails.get();
            User user = new User();
            user.setUsername(userModel.getUsername());
            user.setEmail(userModel.getEmail());
            return user;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }

    public void registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username already taken!");
        }
        ;

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        UserModel user = new UserModel();
        user.setUsername(signupRequest.getUsername());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        userRepository.save(user);
    }
}

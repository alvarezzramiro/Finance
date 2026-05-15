package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.AuthResponse;
import com.ramiro.financeapi.dto.RegisterRequest;
import com.ramiro.financeapi.entity.User;
import com.ramiro.financeapi.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponse register (RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        userRepository.save(user);

        return new AuthResponse("User registered succesfully");
    }
}

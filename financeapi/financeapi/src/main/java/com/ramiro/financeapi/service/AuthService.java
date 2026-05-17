package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.AuthResponse;
import com.ramiro.financeapi.dto.LoginRequest;
import com.ramiro.financeapi.dto.LoginResponse;
import com.ramiro.financeapi.dto.RegisterRequest;
import com.ramiro.financeapi.entity.Role;
import com.ramiro.financeapi.entity.User;
import com.ramiro.financeapi.exception.InvalidCredentialsException;
import com.ramiro.financeapi.repository.UserRepository;
import com.ramiro.financeapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtsService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtsService = jwtsService;
    }

    public AuthResponse register (RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return new AuthResponse("User registered succesfully");
    }

    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        boolean passwordMatches = passwordEncoder.matches(
                request.getPassword(), user.getPassword()
        );

        if (!passwordMatches) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        String token = jwtsService.generateToken(user);

        return new LoginResponse(token);
    }
}

package com.ramiro.financeapi.service;

import com.ramiro.financeapi.dto.*;
import com.ramiro.financeapi.entity.RefreshToken;
import com.ramiro.financeapi.entity.Role;
import com.ramiro.financeapi.entity.User;
import com.ramiro.financeapi.exception.InvalidCredentialsException;
import com.ramiro.financeapi.repository.RefreshTokenRepository;
import com.ramiro.financeapi.repository.UserRepository;
import com.ramiro.financeapi.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtsService;

    private final RefreshTokenRepository refreshTokenRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtsService, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtsService = jwtsService;
        this.refreshTokenRepository = refreshTokenRepository;
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

        String accessToken = jwtsService.generateToken(user);

        String refreshToken = createRefreshToken(user);

        return new LoginResponse(accessToken, refreshToken);
    }

    private String createRefreshToken(User user) {

        String token = UUID.randomUUID().toString();

        RefreshToken refreshToken = new RefreshToken(token, user, LocalDateTime.now().plusDays(7));

        refreshTokenRepository.save(refreshToken);

        return token;
    }

    public RefreshResponse refreshToken(String token) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid refresh token")
                );

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expired");
        }

        String accessToken = jwtsService.generateToken(refreshToken.getUser());

        return new RefreshResponse(accessToken);
    }
}

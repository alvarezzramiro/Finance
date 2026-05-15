package com.ramiro.financeapi.controller;


import com.ramiro.financeapi.dto.AuthResponse;
import com.ramiro.financeapi.dto.LoginRequest;
import com.ramiro.financeapi.dto.LoginResponse;
import com.ramiro.financeapi.dto.RegisterRequest;
import com.ramiro.financeapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(
            @Valid @RequestBody RegisterRequest request
    ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login (
            @Valid @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }
}

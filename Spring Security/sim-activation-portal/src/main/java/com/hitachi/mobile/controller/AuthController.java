package com.hitachi.mobile.controller;

import com.hitachi.mobile.dto.UserRegistrationRequest;
import com.hitachi.mobile.dto.LoginRequest;
import com.hitachi.mobile.dto.LoginResponse;
import com.hitachi.mobile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationRequest request) {
        String result = authService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse result = authService.loginUser(request);
        return ResponseEntity.ok(result);
    }
}

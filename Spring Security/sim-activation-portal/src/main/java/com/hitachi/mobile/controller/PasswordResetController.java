package com.hitachi.mobile.controller;

import com.hitachi.mobile.dto.PasswordResetConfirmRequest;
import com.hitachi.mobile.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

    @Autowired
    private AuthService authService;

    @PostMapping("/confirm")
    public ResponseEntity<String> confirmPasswordReset(@Valid @RequestBody PasswordResetConfirmRequest request) {
        String result = authService.resetPassword(request);
        return ResponseEntity.ok(result);
    }
}

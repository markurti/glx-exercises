package com.hitachi.mobile.service;

import com.hitachi.mobile.dto.UserRegistrationRequest;
import com.hitachi.mobile.dto.LoginRequest;
import com.hitachi.mobile.dto.PasswordResetConfirmRequest;
import com.hitachi.mobile.dto.LoginResponse;
import com.hitachi.mobile.exception.UsernameAlreadyExistsException;
import com.hitachi.mobile.exception.InvalidTokenException;
import com.hitachi.mobile.model.User;
import com.hitachi.mobile.model.Role;
import com.hitachi.mobile.model.PasswordResetToken;
import com.hitachi.mobile.repository.UserRepository;
import com.hitachi.mobile.repository.PasswordResetTokenRepository;
import com.hitachi.mobile.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists: " + request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(Role.USER));
        user.setEnabled(true);

        userRepository.save(user);
        return "User registered successfully";
    }

    public LoginResponse loginUser(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtUtil.generateToken(userDetails);

        return new LoginResponse(token, "Login successful");
    }

    @Transactional
    public String resetPassword(PasswordResetConfirmRequest request) {
        PasswordResetToken resetToken = passwordResetTokenRepository
                .findByTokenAndUsedFalse(request.getToken())
                .orElseThrow(() -> new InvalidTokenException("Invalid or expired token"));

        if (resetToken.isExpired()) {
            throw new InvalidTokenException("Token has expired");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);

        return "Password reset successful";
    }

    public String generatePasswordResetToken(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidTokenException("User not found"));

        passwordResetTokenRepository.deleteByUserId(user.getId());

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(resetToken);

        return token;
    }
}

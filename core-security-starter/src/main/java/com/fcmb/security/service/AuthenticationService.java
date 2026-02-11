package com.fcmb.security.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.fcmb.security.jwt.JwtUtil;
import com.fcmb.shared.entity.User;

public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthenticationService(
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public String authenticate(User user, String rawPassword) {

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateToken(user);
    }
}


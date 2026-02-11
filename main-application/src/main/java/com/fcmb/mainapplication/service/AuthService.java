package com.fcmb.mainapplication.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.fcmb.audit_lib.service.AuditService;
import com.fcmb.mainapplication.dto.CreateUserRequest;
import com.fcmb.mainapplication.dto.LoginRequest;
import com.fcmb.mainapplication.repository.UserRepository;
import com.fcmb.security.service.AuthenticationService;
import com.fcmb.shared.dto.ApiResponse;
import com.fcmb.shared.entity.User;
import com.fcmb.shared.exception.ApplicationException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final AuditService auditService;
    private final HttpServletRequest request;
    private final PasswordEncoder passwordEncoder;

    public ApiResponse<String> getToken(LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = authenticationService.authenticate(user, loginRequest.getPassword());

        request.setAttribute("AUDIT_USERNAME", user.getUsername());
        request.setAttribute("AUDIT_ROLE", user.getRoles().stream().findFirst().map(Enum::name).orElse("NONE"));

        auditService.log(
                user.getUsername(),
                user.getRoles().stream().findFirst().map(Enum::name).orElse("NONE"),
                "USER_LOGIN",
                "User",
                user.getId(),
                null,
                null,
                request.getRemoteAddr(),
                request.getHeader("User-Agent"));

        return ApiResponse.success(token, "Login successful", HttpStatus.OK.value(), request.getRequestURI());
    }

    public ApiResponse<User> getLoggedInUserDetails(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        String username;

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String s) {
            username = s;
        } else {
            throw new RuntimeException("Invalid authentication principal");
        }

        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ApiResponse.success(user, "About me", HttpStatus.OK.value(), request.getRequestURI());
    }




    public ApiResponse<User> createUser(CreateUserRequest dto) {

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(dto.getRoles());
        user.setEnabled(dto.isEnabled());

        User saved = userRepository.save(user);

        return ApiResponse.success(saved, "User created successfully", 201, request.getRequestURI());
    }


}

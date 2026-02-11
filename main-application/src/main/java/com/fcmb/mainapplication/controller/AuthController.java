package com.fcmb.mainapplication.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fcmb.mainapplication.dto.CreateUserRequest;
import com.fcmb.mainapplication.dto.LoginRequest;
import com.fcmb.mainapplication.service.AuthService;
import com.fcmb.shared.dto.ApiResponse;
import com.fcmb.shared.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(authService.getToken(request));
    }

    @GetMapping("/public/health")
    public ResponseEntity<ApiResponse<String>> health(HttpServletRequest request) {
        ApiResponse<String> response = ApiResponse.success(
            "OK",
            "Service is up",
            HttpStatus.OK.value(),
            request.getRequestURI()
        );
        return ResponseEntity.ok(response);
    }


    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse<User>> getLoggedInUser(HttpServletRequest request) {
        return ResponseEntity.ok(authService.getLoggedInUserDetails(request));
    }
    

    @PostMapping("/create/user")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<User> createUser(@Valid @RequestBody CreateUserRequest request) {
        return authService.createUser(request);
    }

    

}

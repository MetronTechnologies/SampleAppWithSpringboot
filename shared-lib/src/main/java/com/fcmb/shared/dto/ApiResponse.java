package com.fcmb.shared.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {


    private boolean success;
    private String message;
    private T data;
    private int status;        // HTTP status code
    private String errorCode;  // Optional application-specific code
    private Instant timestamp;
    private String path;       // Request URI


    // Success factory
    public static <T> ApiResponse<T> success(T data, String message, int status, String path) {
        return new ApiResponse<>(true, message, data, status, null, Instant.now(), path);
    }


    // Failure factory
    public static <T> ApiResponse<T> failure(String message, String errorCode, int status, String path) {
        return new ApiResponse<>(false, message, null, status, errorCode, Instant.now(), path);
    }
}





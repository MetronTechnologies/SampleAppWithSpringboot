package com.fcmb.mainapplication.controller;

import com.fcmb.mainapplication.service.UserAdminService;
import com.fcmb.shared.dto.ApiResponse;
import com.fcmb.shared.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class AdminController {

    private final UserAdminService userAdminService;

    @GetMapping("/api/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<User>> getUsers(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) Set<String> roles,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant createdBefore,
            Pageable pageable
    ) {
        return userAdminService.fetchUsers(username, enabled, roles, createdAfter, createdBefore, pageable, "/api/admin/users");
    }
}





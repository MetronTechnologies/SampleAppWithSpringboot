package com.fcmb.mainapplication.dto;

import com.fcmb.shared.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserFilterDto {

    private String username;
    private Set<Role> roles;
    private Boolean enabled;
    private Instant createdAfter;
    private Instant createdBefore;
}


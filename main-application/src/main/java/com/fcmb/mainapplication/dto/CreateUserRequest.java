package com.fcmb.mainapplication.dto;

import com.fcmb.shared.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


import java.util.Set;


@Getter
@Setter
public class CreateUserRequest {


    @NotBlank
    @Size(min = 3, max = 50)
    private String username;


    @NotBlank
    @Size(min = 6)
    private String password;


    @NotEmpty
    private Set<Role> roles;


    private boolean enabled = true;
}



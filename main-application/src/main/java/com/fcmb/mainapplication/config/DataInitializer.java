package com.fcmb.mainapplication.config;

import java.util.EnumSet;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fcmb.mainapplication.repository.UserRepository;
import com.fcmb.shared.entity.User;
import com.fcmb.shared.enums.Role;

import jakarta.annotation.PostConstruct;

@Configuration
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final String DEFAULT_SUPERUSER = "superadmin";

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    void init() {

        // Only create superuser if it doesn't exist
        boolean exists = userRepository.findByUsername(DEFAULT_SUPERUSER).isPresent();
        if (!exists) {
            User superuser = new User();
            superuser.setUsername(DEFAULT_SUPERUSER);
            superuser.setPassword(passwordEncoder.encode("SuperAdmin@123"));
            superuser.setRoles(EnumSet.allOf(Role.class));
            superuser.setEnabled(true);

            userRepository.save(superuser);

            System.out.println("Default superuser created: username=superadmin, password=SuperAdmin@123");
        } else {
            System.out.println("Superuser already exists: " + DEFAULT_SUPERUSER);
        }
    }
}



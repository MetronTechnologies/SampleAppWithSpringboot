package com.fcmb.security.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fcmb.security.filter.JwtAuthenticationFilter;
import com.fcmb.security.jwt.JwtProperties;
import com.fcmb.security.jwt.JwtUtil;
import com.fcmb.security.service.AuthenticationService;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityAutoConfiguration {

    @Bean
    public JwtUtil jwtUtil(JwtProperties properties) {
        return new JwtUtil(
                properties.getSecret(),
                properties.getExpirationMillis()
        );
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtUtil jwtUtil) {
        return new JwtAuthenticationFilter(jwtUtil);
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthenticationService authenticationService(
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        return new AuthenticationService(passwordEncoder, jwtUtil);
    }
}




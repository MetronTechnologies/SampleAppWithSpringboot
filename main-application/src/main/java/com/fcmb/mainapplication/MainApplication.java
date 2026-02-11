package com.fcmb.mainapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.fcmb.mainapplication",
        "com.fcmb.shared",
        "com.fcmb.security",
        "com.fcmb.audit_lib"
})
@EntityScan(basePackages = {
        "com.fcmb.mainapplication.entity",
        "com.fcmb.shared.entity", // shared-lib entities
        "com.fcmb.audit_lib.entity" // audit-lib entities
})
@EnableJpaRepositories(basePackages = {
        "com.fcmb.mainapplication.repository",
        "com.fcmb.shared.repository", // if you have any shared-lib repositories
        "com.fcmb.audit_lib.repository"
})
@ComponentScan(basePackages = {
        "com.fcmb.mainapplication",
        "com.fcmb.shared", // shared-lib components
        "com.fcmb.audit_lib",
        "com.fcmb.security", // audit-lib components
})

public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}

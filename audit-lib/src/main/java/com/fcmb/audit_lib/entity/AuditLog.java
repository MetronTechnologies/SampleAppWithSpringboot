package com.fcmb.audit_lib.entity;


import com.fcmb.shared.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog extends BaseEntity {

    @Column(nullable = false)
    private String username;      // User performing the action

    @Column(nullable = false)
    private String role;          // Role of the user

    @Column(nullable = false)
    private String action;        // Action type: CREATE, UPDATE, LOGIN, etc.

    @Column(nullable = false)
    private String entity;

    @Column(nullable = true)
    private Long entityId;

    @Column(columnDefinition = "TEXT")
    private String oldValue;

    @Column(columnDefinition = "TEXT")
    private String newValue;      // JSON of entity after change

    @Column(nullable = true)
    private String ipAddress;     // Optional: user IP

    @Column(nullable = true)
    private String userAgent;     // Optional: client info
}


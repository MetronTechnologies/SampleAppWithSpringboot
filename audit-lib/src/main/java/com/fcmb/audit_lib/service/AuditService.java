package com.fcmb.audit_lib.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fcmb.audit_lib.entity.AuditLog;
import com.fcmb.audit_lib.repository.AuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuditService {

    private final AuditRepository auditRepository;
    private final ObjectMapper objectMapper;

    /**
     * Log an audit event asynchronously.
     * All user info and metadata are passed from the main application.
     */
    @Async
    public void log(String username,
                    String role,
                    String action,
                    String entity,
                    Long entityId,
                    Object oldValue,
                    Object newValue,
                    String ipAddress,
                    String userAgent) {

        AuditLog logEntry = new AuditLog();
        logEntry.setUsername(username);
        logEntry.setRole(role);
        logEntry.setAction(action);
        logEntry.setEntity(entity);
        logEntry.setEntityId(entityId);

        try {
            if (oldValue != null) logEntry.setOldValue(objectMapper.writeValueAsString(oldValue));
            if (newValue != null) logEntry.setNewValue(objectMapper.writeValueAsString(newValue));
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize audit object", e);
        }

        logEntry.setIpAddress(ipAddress);
        logEntry.setUserAgent(userAgent);

        auditRepository.save(logEntry);
    }
}




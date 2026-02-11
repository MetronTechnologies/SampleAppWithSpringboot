package com.fcmb.audit_lib.aspect;

import com.fcmb.audit_lib.annotation.Auditable;
import com.fcmb.audit_lib.service.AuditService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final AuditService auditService;
    private final HttpServletRequest request;

    /**
     * Intercepts any method annotated with @Auditable.
     * User info, roles, and other metadata are passed from main application.
     */
    @AfterReturning("@annotation(auditable)")
    public void auditMethod(JoinPoint joinPoint, Auditable auditable) {

        // Metadata passed from main application (replace placeholders)
        String username = (String) request.getAttribute("AUDIT_USERNAME");
        String role = (String) request.getAttribute("AUDIT_ROLE");
        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");

        // Entity info
        String entityName = joinPoint.getSignature().getDeclaringTypeName();
        Long entityId = null; // Can extract ID from method args if desired

        auditService.log(username, role, auditable.action(), entityName, entityId, null, null, ipAddress, userAgent);
    }
}



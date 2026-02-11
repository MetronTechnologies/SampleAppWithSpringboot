package com.fcmb.audit_lib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcmb.audit_lib.entity.AuditLog;

@Repository
public interface AuditRepository extends JpaRepository<AuditLog, Long> {
}


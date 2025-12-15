package com.jojolaptech.camel.repository.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.AuditRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRecordRepository extends JpaRepository<AuditRecord, Long> {}


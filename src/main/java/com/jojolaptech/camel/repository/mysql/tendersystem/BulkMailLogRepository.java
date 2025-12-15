package com.jojolaptech.camel.repository.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.BulkMailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BulkMailLogRepository extends JpaRepository<BulkMailLog, Long> {}


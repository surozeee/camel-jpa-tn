package com.jojolaptech.camel.repository.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.CallLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallLogRepository extends JpaRepository<CallLog, Long> {}


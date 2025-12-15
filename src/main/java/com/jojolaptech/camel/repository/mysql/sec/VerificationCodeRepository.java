package com.jojolaptech.camel.repository.mysql.sec;

import com.jojolaptech.camel.model.mysql.sec.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {}


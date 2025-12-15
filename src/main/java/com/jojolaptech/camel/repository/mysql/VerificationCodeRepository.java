package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.sec.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
}



package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.PaymentRuleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgPaymentRuleRepository")
public interface PaymentRuleRepository extends JpaRepository<PaymentRuleEntity, UUID> {
    boolean existsByMysqlId(Long mysqlId);
}



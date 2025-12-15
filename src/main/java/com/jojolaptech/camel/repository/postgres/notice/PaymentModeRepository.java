package com.jojolaptech.camel.repository.postgres.notice;

import com.jojolaptech.camel.model.postgres.notice.PaymentModeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("pgPaymentModeRepository")
public interface PaymentModeRepository extends JpaRepository<PaymentModeEntity, UUID> {}



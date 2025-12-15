package com.jojolaptech.camel.repository.mysql.payment;

import com.jojolaptech.camel.model.mysql.payment.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {}


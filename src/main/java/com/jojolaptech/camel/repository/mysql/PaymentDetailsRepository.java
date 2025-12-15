package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.payment.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, Long> {
}



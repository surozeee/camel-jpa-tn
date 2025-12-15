package com.jojolaptech.camel.repository.mysql.payment;

import com.jojolaptech.camel.model.mysql.payment.PayPalTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayPalTransactionRepository extends JpaRepository<PayPalTransaction, Long> {}


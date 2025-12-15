package com.jojolaptech.camel.repository.mysql.payment;

import com.jojolaptech.camel.model.mysql.payment.PaymentCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentCurrencyRepository extends JpaRepository<PaymentCurrency, Long> {}


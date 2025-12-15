package com.jojolaptech.camel.repository.mysql.payment;

import com.jojolaptech.camel.model.mysql.payment.PayByOnlineTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayByOnlineTransactionRepository
        extends JpaRepository<PayByOnlineTransaction, Long> {}


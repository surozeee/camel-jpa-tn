package com.jojolaptech.camel.repository.mysql.payment;

import com.jojolaptech.camel.model.mysql.payment.CreditDebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditDebitTransactionRepository
        extends JpaRepository<CreditDebitTransaction, Long> {}


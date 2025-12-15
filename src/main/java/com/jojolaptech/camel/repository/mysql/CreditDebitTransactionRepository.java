package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.payment.CreditDebitTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDebitTransactionRepository extends JpaRepository<CreditDebitTransaction, Long> {
}



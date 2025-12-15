package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.payment.PayByOnlineTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayByOnlineTransactionRepository extends JpaRepository<PayByOnlineTransaction, Long> {
}



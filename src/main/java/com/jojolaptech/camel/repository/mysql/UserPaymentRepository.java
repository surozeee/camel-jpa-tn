package com.jojolaptech.camel.repository.mysql;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    UserPayment findFirstBySecUserOrderByExpireDateDesc(SecUser source);

    @Query("SELECT up FROM UserPayment up LEFT JOIN FETCH up.payPlan")
    Page<UserPayment> findAllWithPayPlan(Pageable pageable);
}



package com.jojolaptech.camel.model.mysql.payment;

import com.jojolaptech.camel.model.mysql.enums.PboPaymentStatus;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pay_by_online_transaction")
@Getter
@Setter
public class PayByOnlineTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pbo_transaction_id")
    private String pboTransactionId;

    @Column(name = "payment_date")
    private Instant paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "pbo_payment_status")
    private PboPaymentStatus pboPaymentStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_id")
    private UserPayment userPayment;
}


package com.jojolaptech.camel.model.mysql.payment;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pay_pal_transaction")
@Getter
@Setter
public class PayPalTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @Column
    private Double amount;

    @Lob
    @Column(name = "paypal_response")
    private String payPalResponse;

    @Enumerated(EnumType.STRING)
    @Column(name = "paypal_status")
    private PayPalStatus payPalStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "paypal_payment_id")
    private String payPalPaymentId;
}


package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "merchant_payment")
@Getter
@Setter
public class MerchantPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_amount")
    private Double payAmount;

    @Column(name = "partner_code")
    private String partnerCode;

    @Column(name = "comm_amt")
    private Double commAmt;

    @Column(name = "comm_rate")
    private Double commRate;

    @Column(name = "pay_date")
    private Instant payDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paying_user_id")
    private SecUser payingUser;
}


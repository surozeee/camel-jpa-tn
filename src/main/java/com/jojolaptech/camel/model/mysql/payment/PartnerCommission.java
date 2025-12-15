package com.jojolaptech.camel.model.mysql.payment;

import com.jojolaptech.camel.model.mysql.partner.PartnerInformation;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
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
@Table(name = "partner_commission")
@Getter
@Setter
public class PartnerCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commission_percent")
    private Double commissionPercent;

    @Column(name = "commission_amount")
    private Double commissionAmount;

    @Column(name = "is_claimed")
    private Boolean isClaimed;

    @Column(name = "user_paid_amount")
    private Double userPaidAmount;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_information_id")
    private PartnerInformation partnerInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_id")
    private UserPayment userPayment;
}


package com.jojolaptech.camel.model.mysql.partner;

import com.jojolaptech.camel.model.mysql.enums.CommissionAssignedCurrency;
import com.jojolaptech.camel.model.mysql.enums.CommissionAssignedMethod;
import com.jojolaptech.camel.model.mysql.enums.CommissionType;
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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tender_partner_commission")
@Getter
@Setter
public class TenderPartnerCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commission_value")
    private Double commissionValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_type")
    private CommissionType commissionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_assigned_method")
    private CommissionAssignedMethod commissionAssignedMethod;

    @Column(name = "is_claimed")
    private Boolean isClaimed;

    @Column(name = "user_paid_amount")
    private Double userPaidAmount;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column
    private String remarks;

    @Column(name = "assigned_commission_amount")
    private Double assignedCommissionAmount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_commission_claimed_details_id")
    private PartnerCommissionClaimedDetails partnerCommissionClaimedDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_assigned_currency")
    private CommissionAssignedCurrency commissionAssignedCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_information_id")
    private PartnerInformation partnerInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_payment_id")
    private UserPayment userPayment;
}


package com.jojolaptech.camel.model.mysql.partner;

import com.jojolaptech.camel.model.mysql.enums.CommissionType;
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
@Table(name = "partner_commission_rule")
@Getter
@Setter
public class PartnerCommissionRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "commission_value")
    private Double commissionValue;

    @Column(name = "commission_value_usd")
    private Double commissionValueUsd;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "commission_percent")
    private Double commissionPercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_type")
    private CommissionType commissionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_information_id")
    private PartnerInformation partnerInformation;
}


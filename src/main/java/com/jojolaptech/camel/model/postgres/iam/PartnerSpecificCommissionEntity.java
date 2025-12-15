package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.ValueTypeEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "partner_specific_commisison")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartnerSpecificCommissionEntity extends BaseAuditEntity {

    private BigDecimal amount;
    private UUID currencyId;

    @Enumerated(EnumType.STRING)
    private ValueTypeEnum commissionType;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private UserEntity partner;
}


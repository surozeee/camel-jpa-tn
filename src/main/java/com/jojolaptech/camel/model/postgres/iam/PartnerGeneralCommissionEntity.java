package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.ValueTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "partner_general_commisison")
public class PartnerGeneralCommissionEntity extends BaseAuditEntity {

    private BigDecimal amountRangeFrom;
    private BigDecimal amountRangeTo;
    private UUID currencyId;

    @Enumerated(EnumType.STRING)
    private ValueTypeEnum type;

    private BigDecimal value;


}


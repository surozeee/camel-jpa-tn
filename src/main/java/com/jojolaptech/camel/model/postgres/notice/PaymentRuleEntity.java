package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.ValueTypeEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payment_rule")
public class PaymentRuleEntity extends BaseAuditEntity {

    private int periodInMonth;
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private ValueTypeEnum valueType;

    private BigDecimal discount;
    private BigDecimal netPrice;
}
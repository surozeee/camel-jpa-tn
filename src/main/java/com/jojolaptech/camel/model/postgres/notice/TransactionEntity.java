package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.DiscountTypeEnum;
import com.jojolaptech.camel.model.postgres.enums.PaymentModeEnum;
import com.jojolaptech.camel.model.postgres.enums.TransactionStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity extends BaseAuditEntity {

    @Column(name = "mysql_id", unique = true)
    private Long mysqlId;

    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentModeEnum paymentMode;

    private BigDecimal grossAmount;
    private BigDecimal netAmountPaid;
    @Enumerated(EnumType.STRING)
    private DiscountTypeEnum discountType;
    private BigDecimal discount;
    private LocalDate paymentDate;
    private String note;
    private UUID userId;
    private String receipt;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;

    private UUID paymentRuleId;

    private String rejectReason;
    private LocalDate expiryDate;
    private LocalDate approveRejectDate;
}

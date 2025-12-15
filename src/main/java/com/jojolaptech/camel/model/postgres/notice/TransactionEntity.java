package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.PaymentModeEnum;
import com.tendernotice.tenderservice.enums.TransactionStatusEnum;
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

    private String referenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentModeEnum paymentMode;

    private BigDecimal amount;
    private LocalDate paymentDate;
    private String note;
    private UUID userId;
    private String receipt;

    @Enumerated(EnumType.STRING)
    private TransactionStatusEnum status;


    private UUID paymentRuleId;

    private String rejectReason;

}

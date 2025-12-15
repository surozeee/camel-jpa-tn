package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.PaymentModeEnum;
import com.tendernotice.tenderservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "payment_method")
public class PaymentModeEntity extends BaseAuditEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentModeEnum paymentMode;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}


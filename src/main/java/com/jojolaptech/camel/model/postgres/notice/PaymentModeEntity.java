package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.PaymentModeEnum;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
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



package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseEntity;
import com.tendernotice.tenderservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency")
public class CurrencyEntity extends BaseEntity {

    private String name;
    private String code;
    private String symbol;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}

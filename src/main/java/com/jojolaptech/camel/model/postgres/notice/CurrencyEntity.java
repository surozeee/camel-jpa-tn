package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "currency")
public class CurrencyEntity extends BaseAuditEntity {

    private String name;
    private String code;
    private String symbol;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}


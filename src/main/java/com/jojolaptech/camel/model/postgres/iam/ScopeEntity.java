package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
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

@Entity
@Table(name = "scopes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScopeEntity extends BaseAuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        this.status = StatusEnum.ACTIVE;
    }
}


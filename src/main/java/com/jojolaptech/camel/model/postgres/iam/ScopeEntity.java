package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

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

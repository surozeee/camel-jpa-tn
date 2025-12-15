package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "industry")
public class IndustryEntity extends BaseAuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void  prePersist() {
        status = StatusEnum.ACTIVE;
    }

}

package com.jojolaptech.camel.model.postgres.notice;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tips_category")
public class TipsCategoryEntity extends BaseAuditEntity {

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;
}

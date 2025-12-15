package com.jojolaptech.camel.model.postgres.notice;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tips")
@Builder
public class TipsEntity extends BaseAuditEntity {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private TipsCategoryEntity category;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}

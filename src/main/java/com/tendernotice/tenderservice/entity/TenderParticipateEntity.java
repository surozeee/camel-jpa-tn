package com.tendernotice.tenderservice.entity;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.notice.TenderNoticeEntity;
import com.tendernotice.tenderservice.enums.YesNoEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "participate")
public class TenderParticipateEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private YesNoEnum awarded;

    @Enumerated(EnumType.STRING)
    private YesNoEnum participated;

    private UUID userId;
    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> tags;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_notice_id")
    private TenderNoticeEntity tenderNotice;


}

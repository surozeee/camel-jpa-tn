package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notice_bookmark")
public class NoticeBookmarkEntity extends BaseAuditEntity {

    @Column(name = "mysql_id", unique = true)
    private Long mysqlId;

    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "notice_id")
    private TenderNoticeEntity notice;
}


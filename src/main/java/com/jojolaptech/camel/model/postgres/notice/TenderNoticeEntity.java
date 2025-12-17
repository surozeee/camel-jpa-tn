package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.DateFormatEnum;
import com.jojolaptech.camel.model.postgres.enums.TenderNoticeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tender_notice")
public class TenderNoticeEntity extends BaseAuditEntity {

    @Enumerated(EnumType.STRING)
    private DateFormatEnum dateFormat;

    @Column(name = "mysql_id", unique = true)
    private Long mysqlId;

    @Column(nullable = false)
    private UUID sourceId;
    private UUID districtId;

    @Column(nullable = false)
    private UUID categoryId;

    @Column(nullable = false)
    private UUID industryId;

    @Column(nullable = false)
    private UUID productServiceId;

    @Column(columnDefinition = "TEXT")
    private String noticeProvider;
    private String description;
    private LocalDateTime publishDate;
    private Integer submitUptoDays;
    private LocalDateTime submitDate;
    private String remarks;
    private String noticeImage;
    private String document;
    private LocalDateTime emailTime;

    @Column(name = "is_linked_notice")
    private boolean linkedNotice;

    @Enumerated(EnumType.STRING)
    private TenderNoticeStatus status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent_notice_id")
    private TenderNoticeEntity parentNotice;


}


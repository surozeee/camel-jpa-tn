package com.jojolaptech.camel.model.storage.entity;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.storagealertservice.enums.CustomEmailEnum;
import com.tendernotice.storagealertservice.enums.NotificationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "custom_email")
@Builder
public class CustomEmailEntity extends BaseAuditEntity {

    private String subject;
    @Column(columnDefinition = "TEXT")
    private  String body;

    @Enumerated(EnumType.STRING)
    private CustomEmailEnum customEmailTo;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> recipients;

    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum status;
}

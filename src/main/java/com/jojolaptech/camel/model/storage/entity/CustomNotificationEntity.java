package com.jojolaptech.camel.model.storage.entity;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.storagealertservice.enums.NotificationChannelEnum;
import com.tendernotice.storagealertservice.enums.NotificationStatusEnum;
import com.tendernotice.storagealertservice.enums.NotificationTypeEnum;
import com.tendernotice.storagealertservice.payload.response.AppWebNotificationResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "custom_notification")
public class CustomNotificationEntity extends BaseAuditEntity {

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationChannelEnum notificationChannel;

    private LocalDateTime sendDate;

    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum status;

    @Enumerated(EnumType.STRING)
    private NotificationTypeEnum type;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private AppWebNotificationResponse appNotification;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private AppWebNotificationResponse webNotification;
}

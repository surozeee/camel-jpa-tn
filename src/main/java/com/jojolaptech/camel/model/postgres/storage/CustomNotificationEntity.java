package com.jojolaptech.camel.model.postgres.storage;


import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.NotificationChannelEnum;
import com.jojolaptech.camel.model.postgres.enums.NotificationStatusEnum;
import com.jojolaptech.camel.model.postgres.enums.NotificationTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

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
    private Map<String, Object> appNotification;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> webNotification;
}


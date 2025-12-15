package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.NotificationChannelEnum;
import com.jojolaptech.camel.model.postgres.enums.NotificationStatusEnum;
import com.jojolaptech.camel.model.postgres.enums.NotificationTypeEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private String appNotification;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String webNotification;
}


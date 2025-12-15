package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "notification_log")
@Getter
@Setter
public class NotificationLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text")
    private String message;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "total_sent")
    private Integer totalSent;

    @Column(name = "sucess_count")
    private Integer successCount;

    @Column(name = "total_app_token")
    private Integer totalAppToken;

    @Column(name = "app_success_count")
    private Integer appSuccessCount;

    @Column(name = "total_web_token")
    private Integer totalWebToken;

    @Column(name = "web_success_count")
    private Integer webSuccessCount;

    @Column(name = "total_user")
    private Integer totalUser;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
}


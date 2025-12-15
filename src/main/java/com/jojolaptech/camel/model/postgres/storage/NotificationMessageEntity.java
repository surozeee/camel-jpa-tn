package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.enums.NotificationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification_message")
@Builder
public class NotificationMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String body;

    private String recipient;

    private String channel;

    private String language;

    private String topic;

    @Enumerated(EnumType.STRING)
    private NotificationStatusEnum status;

    @Column(columnDefinition = "TEXT")
    private String responseMessage;

    private LocalDateTime sendDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @CreatedBy
    private UUID createdBy;
}

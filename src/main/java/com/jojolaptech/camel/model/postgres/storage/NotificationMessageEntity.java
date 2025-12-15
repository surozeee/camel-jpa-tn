package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.enums.ChannelEnum;
import com.jojolaptech.camel.model.postgres.enums.LanguageEnum;
import com.jojolaptech.camel.model.postgres.enums.NotificationStatusEnum;
import com.jojolaptech.camel.model.postgres.enums.TopicEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @Enumerated(EnumType.STRING)
    private ChannelEnum channel;

    @Enumerated(EnumType.STRING)
    private LanguageEnum language;

    @Enumerated(EnumType.STRING)
    private TopicEnum topic;

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

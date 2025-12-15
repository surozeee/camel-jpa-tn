package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.enums.OtpStatusEnum;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "otp")
public class OtpEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String recipient;
    private String code;

    @Enumerated(EnumType.STRING)
    private TopicEnum topic;

    @Enumerated(EnumType.STRING)
    private OtpStatusEnum status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

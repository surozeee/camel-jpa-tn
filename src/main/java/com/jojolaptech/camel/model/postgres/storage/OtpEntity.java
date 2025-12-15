package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.enums.OtpStatusEnum;
import jakarta.persistence.*;
import lombok.*;
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

    private String topic;

    @Enumerated(EnumType.STRING)
    private OtpStatusEnum status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

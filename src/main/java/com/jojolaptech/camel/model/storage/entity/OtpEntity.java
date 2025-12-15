package com.jojolaptech.camel.model.storage.entity;

import com.tendernotice.core.enums.TopicEnum;
import com.tendernotice.storagealertservice.enums.OtpStatusEnum;
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

    @Enumerated(EnumType.STRING)
    private TopicEnum topic;

    @Enumerated(EnumType.STRING)
    private OtpStatusEnum status;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

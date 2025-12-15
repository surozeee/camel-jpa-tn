package com.jojolaptech.camel.model.postgres.iam;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.PlatformEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "device_info")
public class DeviceInfoEntity extends BaseAuditEntity {

    private String deviceId;

    @Enumerated(EnumType.STRING)
    private PlatformEnum platform;

    @Column(length = 512)
    private String fcmToken;
    private String deviceName;

    @Column(name = "os_version")
    private String osVersion;
    private String appVersion;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private UserEntity user;

}

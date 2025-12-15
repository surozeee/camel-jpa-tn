package com.jojolaptech.camel.model.storage.entity;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.storagealertservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertisement_banner")
public class AdvertisementBannerEntity extends BaseAuditEntity {

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String advertisementMiddleUrl;

    private String bannerMiddle;
    @Column(columnDefinition = "TEXT")
    private String advertisementTopUrl;
    private String bannerTop;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}

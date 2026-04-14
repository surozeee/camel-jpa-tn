package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Single-slot banner model aligned with Postgres {@code advertisement_url} / {@code banner} (merged from legacy top/middle).
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advertisement_banner")
public class AdvertisementBannerEntity extends BaseAuditEntity {

    @Column(name = "mysql_id", unique = true)
    private Long mysqlId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String advertisementUrl;

    @Column(columnDefinition = "TEXT")
    private String banner;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }
}

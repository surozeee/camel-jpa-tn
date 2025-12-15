package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tender_option_setting")
public class TenderOptionSettingEntity extends BaseAuditEntity {

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<UUID> newspaperSetting;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<UUID> categorySetting;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<UUID> productServiceSetting;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<UUID> industrySetting;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_info_id")
    private UserInfoEntity userInfo;
}

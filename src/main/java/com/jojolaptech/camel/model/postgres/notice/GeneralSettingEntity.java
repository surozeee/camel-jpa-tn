package com.jojolaptech.camel.model.postgres.notice;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.tenderservice.enums.StatusEnum;
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
@Table(name = "general_setting")
public class GeneralSettingEntity extends BaseAuditEntity {

    private String name;
    private String key;
    private String value;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    private String content;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }

}

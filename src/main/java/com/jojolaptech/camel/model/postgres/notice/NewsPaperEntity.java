package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
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
@Table(name = "news_paper")
public class NewsPaperEntity extends BaseAuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void  prePersist() {
        status = StatusEnum.ACTIVE;
    }

}


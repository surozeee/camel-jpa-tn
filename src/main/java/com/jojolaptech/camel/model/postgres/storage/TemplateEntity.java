package com.jojolaptech.camel.model.postgres.storage;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.ChannelEnum;
import com.jojolaptech.camel.model.postgres.enums.LanguageEnum;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import com.jojolaptech.camel.model.postgres.enums.TopicEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "template")
public class TemplateEntity extends BaseAuditEntity {

    @Serial
    private static final long serialVersionUID = -1639865605883233827L;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private TopicEnum topic;

    @Enumerated(EnumType.STRING)
    private LanguageEnum language;

    @Enumerated(EnumType.STRING)
    private ChannelEnum channel;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @PrePersist
    public void prePersist() {
        this.status = StatusEnum.ACTIVE;
    }
}


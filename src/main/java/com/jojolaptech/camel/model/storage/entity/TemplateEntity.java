package com.jojolaptech.camel.model.storage.entity;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.core.enums.ChannelEnum;
import com.tendernotice.core.enums.LanguageEnum;
import com.tendernotice.core.enums.TopicEnum;
import com.tendernotice.storagealertservice.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

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

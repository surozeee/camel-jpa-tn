package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class TagsEntity extends BaseAuditEntity {

    private String name;
    private UUID userId;
    private UUID noticeId;

}

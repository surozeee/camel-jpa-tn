package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "resources")
public class ResourceEntity extends BaseAuditEntity {

    @Column(nullable = false, length = 150)
    private String name;

    @Column(length = 100)
    private String code;

    @Column(nullable = false, length = 100)
    private String path;

    @Column(nullable = false)
    private Integer ordered;

    @Column(length = 100)
    private String icon;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "parent_resource_id")
    private ResourceEntity parentResource;

    @OneToMany(mappedBy = "parentResource", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ResourceEntity> childrenResources = new ArrayList<>();

    @OneToMany(mappedBy = "resource", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<PermissionEntity> permissions = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.status = StatusEnum.ACTIVE;
    }

}
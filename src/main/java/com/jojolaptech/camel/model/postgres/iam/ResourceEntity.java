package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

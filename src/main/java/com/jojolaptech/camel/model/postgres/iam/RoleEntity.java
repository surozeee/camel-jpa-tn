package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity extends BaseAuditEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;


    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns =
    @JoinColumn(name = "permission_id"))
    private List<PermissionEntity> permissions;

    @PrePersist
    public void prePersist() {
        status = StatusEnum.ACTIVE;
    }

}

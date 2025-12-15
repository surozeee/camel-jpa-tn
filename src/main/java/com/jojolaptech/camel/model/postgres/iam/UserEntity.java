package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.UserStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_username", columnList = "username")
})
public class UserEntity extends BaseAuditEntity {

    @Column(name = "username", unique = true, nullable = false, length = 150)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private UserStatusEnum status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>();


    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserInfoEntity userInfo;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private DeviceInfoEntity deviceInfo;


    @PrePersist
    public void prePersist() {
        if (this.status == null) {
            this.status = UserStatusEnum.PENDING;
        }
    }


}

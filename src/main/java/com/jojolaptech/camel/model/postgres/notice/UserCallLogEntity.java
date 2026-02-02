package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.CallActionEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_call_logs")
public class UserCallLogEntity extends BaseAuditEntity {

    @Column(name = "mysql_id", unique = true)
    private Long mysqlId;

    @Enumerated(EnumType.STRING)
    @Column(name = "action", nullable = false, length = 50)
    private CallActionEnum action;

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
}

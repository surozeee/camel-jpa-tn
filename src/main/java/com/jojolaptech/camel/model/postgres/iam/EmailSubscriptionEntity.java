package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "email_subscription")
public class EmailSubscriptionEntity extends BaseAuditEntity {

    @Column(name = "is_subscribed")
    private boolean subscribed;

    private boolean deliverNoticeWithImageEnabled;
    private Integer dayToAlert;
    private boolean tipsEnabled;
    private boolean newsletterEnabled;
    private LocalDateTime unsubscribeDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user__info_id")
    private UserInfoEntity userInfo;

}


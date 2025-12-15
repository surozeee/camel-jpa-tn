package com.jojolaptech.camel.model.postgres.iam;


import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.PartnerStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "referral_user")
public class ReferralUserEntity extends BaseAuditEntity {

    private String referralCode;
    private Long clientCount;

    @Enumerated(EnumType.STRING)
    private PartnerStatusEnum status;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "referred_user_id")
    private UserEntity referredUser;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "referral_user_id")
    private UserEntity referrerUser; // who gave the referral code

}

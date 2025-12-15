package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.AuthProviderEnum;
import com.tendernotice.identityservice.usermodule.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_info")
public class UserInfoEntity extends BaseAuditEntity {

    private String name;
    private String emailAddress;
    private String mobileNumber;
    private String telePhoneNumber;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private LocalDate dateofBirth;
    private String address;
    private String profileImage;
    private String referralCode;
    private LocalDateTime subscriptionExpiryDate;
    private LocalDateTime lastLoggedIn;

    @Enumerated(EnumType.STRING)
    private AuthProviderEnum authProvider;

    @Column(name = "is_pro_subscription")
    private boolean proSubscription;

    @OneToOne(mappedBy = "userInfo", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private TenderOptionSettingEntity tenderOptionSetting;

    @OneToOne(mappedBy = "userInfo", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private EmailSubscriptionEntity emailSubscription;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private UserEntity user;
}

package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.AuthProviderEnum;
import com.jojolaptech.camel.model.postgres.enums.GenderEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String secondaryEmail;
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

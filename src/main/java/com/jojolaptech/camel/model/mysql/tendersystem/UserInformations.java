package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.sec.SecUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_informations")
@Getter
@Setter
public class UserInformations {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String address;

    @Column
    private String tel;

    @Column
    private String mobile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @Column(name = "filter_newspaper", columnDefinition = "text")
    private String filterNewspaper;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "day_to_participate")
    private Integer dayToParticipate;

    @Column(name = "filter_industry", columnDefinition = "text")
    private String filterIndustry;

    @Column(name = "filter_product_service", columnDefinition = "text")
    private String filterProductService;

    @Column(name = "filter_category", columnDefinition = "text")
    private String filterCategory;

    @Column(name = "filter_tender_classification", columnDefinition = "text")
    private String filterTenderClassification;

    @Column(name = "filter_tags", columnDefinition = "text")
    private String filterTags;

    @Column(name = "show_tips")
    private Boolean showTips = true;

    @Column(name = "is_email_attached")
    private Boolean isEmailAttached = false;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified = false;

    @Column(name = "registration_date")
    private Instant registrationDate;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "last_mail_received_date")
    private Instant lastMailReceivedDate;

    @OneToMany(mappedBy = "userInfo")
    private Set<UsersContactList> userContactList = new HashSet<>();
}


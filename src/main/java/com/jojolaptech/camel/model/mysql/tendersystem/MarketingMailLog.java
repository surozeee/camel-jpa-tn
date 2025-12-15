package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.tendersystem.growNetwork.EmailCampaign;
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
@Table(name = "marketing_mail_log")
@Getter
@Setter
public class MarketingMailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sending_email")
    private String sendingEmail;

    @Column(name = "email_label")
    private String emailLabel;

    @Column
    private String subject;

    @Column(name = "send_date")
    private Instant sendDate;

    @Column(name = "delivered_date")
    private Instant deliveredDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email_campaign_id")
    private EmailCampaign emailCampaign;

    @OneToMany(mappedBy = "marketingMailLog")
    private Set<MarketingMailUserLog> marketingMailUserLog = new HashSet<>();
}


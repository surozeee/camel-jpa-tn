package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marketing_mail_user_log")
@Getter
@Setter
public class MarketingMailUserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_address")
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_mail_send_status")
    private UserMailSendStatus userMailSendStatus;

    @Column(name = "send_date")
    private Instant sendDate;

    @Lob
    @Column(name = "error_response")
    private String errorResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marketing_mail_log_id")
    private MarketingMailLog marketingMailLog;
}


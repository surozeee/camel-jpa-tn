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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "thread_mail_log")
@Getter
@Setter
public class ThreadMailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "thread_mail_category")
    private ThreadMailCategory threadMailCategory;

    @Column(name = "receiver_email_address")
    private String receiverEmailAddress;

    @Column(name = "send_date")
    private Instant sendDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_mail_content_id")
    private ThreadMailContent threadMailContent;
}


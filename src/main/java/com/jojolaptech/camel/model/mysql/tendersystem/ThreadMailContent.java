package com.jojolaptech.camel.model.mysql.tendersystem;

import com.jojolaptech.camel.model.mysql.enums.MailType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "thread_mail_content")
@Getter
@Setter
public class ThreadMailContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mail_code", unique = true, nullable = false)
    private String mailCode;

    @Column(name = "mail_name")
    private String mailName;

    @Column(name = "mail_content", columnDefinition = "text")
    private String mailContent;

    @Column(name = "mail_from")
    private String mailFrom;

    @Column(name = "hash_value")
    private String hashValue;

    @Column(name = "mail_label")
    private String mailLabel;

    @Column
    private String subject;

    @Enumerated(EnumType.STRING)
    @Column(name = "mail_type")
    private MailType mailType;

    @Column(name = "mail_day")
    private int mailDay;

    @Column(name = "recursive_day")
    private int recursiveDay;

    @Column(name = "send_thread_mail")
    private Boolean sendThreadMail;

    @Column(name = "created_date")
    private Instant createdDate = Instant.now();
}


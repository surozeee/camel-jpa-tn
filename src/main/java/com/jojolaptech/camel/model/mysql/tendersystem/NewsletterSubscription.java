package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "newsletter_subscription")
@Getter
@Setter
public class NewsletterSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_address", unique = true, nullable = false)
    private String emailAddress;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "unsubscribe_date")
    private Instant unsubscribeDate;
}


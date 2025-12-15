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
@Table(name = "contact_details")
@Getter
@Setter
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email_address", unique = true)
    private String emailAddress;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "unsubscribed_date")
    private Instant unSubscribedDate;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column
    private String photo;

    @Enumerated(EnumType.STRING)
    @Column(name = "generated_from")
    private GeneratedFrom generatedFrom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_type_id")
    private ContactType contactType;
}


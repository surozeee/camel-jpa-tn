package com.jojolaptech.camel.model.mysql.tendersystem;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "other_bidder")
@Getter
@Setter
public class OtherBidder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bidder_name", nullable = false)
    private String bidderName;

    @Column(name = "bidder_address")
    private String bidderAddress;

    @Column(name = "bidder_telephone")
    private String bidderTelephone;

    @Column(name = "bidder_mobile")
    private String bidderMobile;

    @Column(name = "bidder_email")
    private String bidderEmail;

    @Column(name = "bidder_amount")
    private Double bidderAmount;

    @Column(name = "bidder_remark")
    private String bidderRemark;

    @Column(name = "bidder_contact_person_name")
    private String bidderContactPersonName;

    @Column(name = "bidder_is_won")
    private Boolean bidderIsWon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pin_id")
    private Pin pin;
}


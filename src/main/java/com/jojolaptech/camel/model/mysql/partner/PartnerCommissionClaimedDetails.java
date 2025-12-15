package com.jojolaptech.camel.model.mysql.partner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "partner_commission_claimed_details")
@Getter
@Setter
public class PartnerCommissionClaimedDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_information_id")
    private PartnerInformation partnerInformation;

    @Column(name = "claimed_amt")
    private Double claimedAmt;

    @Column(name = "claimed_amt_usd")
    private Double claimedAmtUsd;

    @Column(name = "claimed_date")
    private Instant claimedDate;
}


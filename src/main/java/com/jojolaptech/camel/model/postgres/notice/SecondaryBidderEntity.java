package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "secondary_bidder")
public class SecondaryBidderEntity extends BaseAuditEntity {

    private String name;
    private String address;
    private String mobileNumber;
    private BigDecimal biddingAmount;
    private String remarks;
    private String telePhone;
    private String emailAddress;
    private String contactPerson;

    @Column(name = "is_awarded_to_bidder")
    private boolean awardedToBidder;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "primary_bidder_info_id")
    private PrimaryBidderEntity primaryBidder;

}


package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "primary_bidder")
public class PrimaryBidderEntity extends BaseAuditEntity {

    private UUID userId;
    private String note;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "tender_notice_id")
    private TenderNoticeEntity tenderNotice;

    @OneToOne(mappedBy = "primaryBidder", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private SecondaryBidderEntity secondaryBidders;

    @OneToMany(mappedBy = "primaryBidder", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<BidderDocumentEntity> bidderDocuments;

    @OneToMany(mappedBy = "primaryBidder", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<QuotedAmountEntity> quotedAmounts;



}


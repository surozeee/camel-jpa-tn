package com.jojolaptech.camel.model.postgres.notice;

import com.tendernotice.core.entity.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bidder_document")
public class BidderDocumentEntity extends BaseAuditEntity {

    private String documentType;
    private String document;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "primary_bidder_id")
    private PrimaryBidderEntity primaryBidder;
}

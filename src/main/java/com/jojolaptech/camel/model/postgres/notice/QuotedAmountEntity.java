package com.jojolaptech.camel.model.postgres.notice;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "quoted_amount")
public class QuotedAmountEntity extends BaseAuditEntity {

    private BigDecimal amount;
    private String notes;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "primary_bidder_id")
    private PrimaryBidderEntity primaryBidder;
}


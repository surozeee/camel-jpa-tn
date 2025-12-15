package com.jojolaptech.camel.model.postgres.iam;

import com.tendernotice.core.entity.BaseAuditEntity;
import com.tendernotice.identityservice.usermodule.enums.ValueTypeEnum;
import com.tendernotice.identityservice.usermodule.payload.request.AssignedCommissionRequest;
import com.tendernotice.identityservice.usermodule.payload.request.ClaimedCommissionRequest;
import com.tendernotice.identityservice.usermodule.payload.request.ClientPaymentRequest;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "partner_commisison")
public class PartnerCommissionEntity extends BaseAuditEntity {

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<ClientPaymentRequest> clientPayment;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<ClaimedCommissionRequest> claimedCommission;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<AssignedCommissionRequest> assignedCommission;

    @Enumerated(EnumType.STRING)
    private ValueTypeEnum commissionType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "partner_id")
    private ReferralUserEntity partner;
}

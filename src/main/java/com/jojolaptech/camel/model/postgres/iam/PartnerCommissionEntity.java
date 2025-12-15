package com.jojolaptech.camel.model.postgres.iam;

import com.jojolaptech.camel.model.postgres.BaseAuditEntity;
import com.jojolaptech.camel.model.postgres.enums.ValueTypeEnum;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    private List<String> clientPayment;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<String> claimedCommission;

    @Column(columnDefinition = "jsonb")
    @Type(value = JsonBinaryType.class)
    private List<String> assignedCommission;

    @Enumerated(EnumType.STRING)
    private ValueTypeEnum commissionType;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "partner_id")
    private ReferralUserEntity partner;
}


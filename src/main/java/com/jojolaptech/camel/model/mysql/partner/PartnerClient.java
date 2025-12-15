package com.jojolaptech.camel.model.mysql.partner;

import com.jojolaptech.camel.model.mysql.enums.PartnerAssociatedBy;
import com.jojolaptech.camel.model.mysql.enums.PartnerClientStatus;
import com.jojolaptech.camel.model.mysql.sec.SecUser;
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
@Table(name = "partner_client")
@Getter
@Setter
public class PartnerClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date")
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_client_status")
    private PartnerClientStatus partnerClientStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "partner_associated_by")
    private PartnerAssociatedBy partnerAssociatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_information_id")
    private PartnerInformation partnerInformation;
}


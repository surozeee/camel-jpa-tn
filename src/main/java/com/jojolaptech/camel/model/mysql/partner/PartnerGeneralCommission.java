package com.jojolaptech.camel.model.mysql.partner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "partner_general_commission")
@Getter
@Setter
public class PartnerGeneralCommission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "commission_type")
    private CommissionType commissionType;

    @Column(name = "from_val")
    private Double fromVal;

    @Column(name = "to_val")
    private Double toVal;

    @Column(name = "commission_value")
    private Double commissionValue;
}


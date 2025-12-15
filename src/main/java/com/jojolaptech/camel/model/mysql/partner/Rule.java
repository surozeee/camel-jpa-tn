package com.jojolaptech.camel.model.mysql.partner;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rule")
@Getter
@Setter
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private Integer commission;

    @Column(name = "point_commission")
    private Integer pointCommission;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany(mappedBy = "rule")
    private Set<PartnerRule> partnerRules = new HashSet<>();
}


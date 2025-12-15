package com.jojolaptech.camel.model.mysql.tendersystem;

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
@Table(name = "pay_plan")
@Getter
@Setter
public class PayPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private long money;

    @Column(nullable = false)
    private int discount;

    @Column(nullable = false)
    private long net;

    @Column(name = "usd_amount")
    private Double usdAmount;

    @OneToMany(mappedBy = "payPlan")
    private Set<Invoice> invoices = new HashSet<>();

    @OneToMany(mappedBy = "payPlan")
    private Set<NIBL> nibl = new HashSet<>();

    @OneToMany(mappedBy = "payPlan")
    private Set<UserPayment> userPayments = new HashSet<>();
}
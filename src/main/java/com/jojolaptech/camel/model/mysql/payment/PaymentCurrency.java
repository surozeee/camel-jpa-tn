package com.jojolaptech.camel.model.mysql.payment;

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
@Table(name = "payment_currency")
@Getter
@Setter
public class PaymentCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "currency_name")
    private String currencyName;

    @Column(name = "currency_symbol")
    private String currencySymbol;

    @Column(name = "amount_list")
    private String amountList;

    @Column(name = "min_amount")
    private Double minAmount;

    @Column(name = "iso_code_for_payment", unique = true)
    private String isoCodeForPayment;

    @Column(name = "allowed_for_all_pay_methods")
    private Boolean allowedForAllPayMethods;

    @OneToMany(mappedBy = "paymentCurrency")
    private Set<PaymentDetails> paymentDetails = new HashSet<>();
}


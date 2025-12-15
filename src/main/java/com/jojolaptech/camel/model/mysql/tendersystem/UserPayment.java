package com.jojolaptech.camel.model.mysql.tendersystem;

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
@Table(name = "user_payment")
@Getter
@Setter
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_date")
    private Instant payDate;

    @Column(name = "expire_date")
    private Instant expireDate;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "verify_status")
    private String verifyStatus;

    @Column(name = "vaucher_cheque_id")
    private String voucherChequeId;

    @Column(name = "bank_name")
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "paid_status")
    private PaidStatus paidStatus;

    @Column(name = "verify_date")
    private Instant verifyDate;

    @Column(name = "period_pay_plan")
    private String periodPayPlan;

    @Column(name = "net_pay_plan")
    private long netPayPlan;

    @Column(name = "net_pay_plan_usd")
    private Double netPayPlanUsd;

    @Column(name = "to_pay_amount")
    private Double toPayAmount;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "payment_note")
    private String paymentNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_plan_id")
    private PayPlan payPlan;
}
package com.jojolaptech.camel.model.mysql.tendersystem;

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
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_payment")
@Getter
@Setter
public class UserPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pay_date")
    private Date payDate;

    @Column(name = "expire_date")
    private Date expireDate;

    @Column(name = "pay_type")
    private String payType;

    @Column(name = "verify_status")
    private String verifyStatus;

    @Column(name = "vaucher_cheque_id")
    private String vaucherChequeId;

    @Column(name = "bank_name")
    private String bankName;

    @Enumerated(EnumType.STRING)
    @Column(name = "paid_status")
    private PaidStatus paidStatus;

    @Column(name = "verify_date")
    private Date verifyDate;

    @Column(name = "period_pay_plan")
    private String periodPayPlan;

    @Column(name = "net_pay_plan", nullable = false)
    private long netPayPlan;

    @Column(name = "net_pay_plan_usd")
    private Double netPayPlanUsd;

    @Column(name = "to_pay_amount")
    private Double toPayAmount;

    @Column(name = "discount_percent")
    private Double discountPercent;

    @Column(name = "transaction_id", unique = true)
    private String transactionId;

    @Column(name = "payment_note")
    private String paymentNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_plan_id")
    private PayPlan payPlan;
}


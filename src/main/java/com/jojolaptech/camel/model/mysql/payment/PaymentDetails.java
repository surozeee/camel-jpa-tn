package com.jojolaptech.camel.model.mysql.payment;

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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payment_details")
@Getter
@Setter
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;

    @Lob
    private String comment;

    @Lob
    private String company;

    @Column
    private Double amount;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "pay_country")
    private String payCountry;

    @Column(name = "paid_date")
    private Instant paidDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "paid_from")
    private PaidFrom paidFrom;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "paypal_payment_id")
    private String payPalPaymentId;

    @Lob
    @Column(name = "paypal_response")
    private String payPalResponse;

    @Column(name = "stripe_charge_id")
    private String stripeChargeId;

    @Lob
    @Column(name = "stripe_response")
    private String stripeResponse;

    @Column(name = "stripe_balance_transaction_id")
    private String stripeBalanceTransactionId;

    @Column(name = "stripe_failure_message")
    private String stripeFailureMessage;

    @Column(name = "stripe_failure_code")
    private String stripeFailureCode;

    @Column(name = "is_recurring")
    private Boolean isRecurring;

    @Column(name = "enlist_contribution")
    private Boolean enlistContribution;

    @Column(name = "email_receipt")
    private Boolean emailReceipt;

    @Column(name = "cover_processing_fee")
    private Boolean coverProcessingFee;

    @Column(name = "payment_method_name")
    private String paymentMethodName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_currency_id")
    private PaymentCurrency paymentCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;
}


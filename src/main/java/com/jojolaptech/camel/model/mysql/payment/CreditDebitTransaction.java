package com.jojolaptech.camel.model.mysql.payment;

import com.jojolaptech.camel.model.mysql.enums.CardType;
import com.jojolaptech.camel.model.mysql.enums.PaymentServiceProvider;
import com.jojolaptech.camel.model.mysql.enums.PaymentStatus;
import com.jojolaptech.camel.model.mysql.sec.SecUser;
import com.jojolaptech.camel.model.mysql.tendersystem.PayPlan;
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
@Table(name = "credit_debit_transaction")
@Getter
@Setter
public class CreditDebitTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_user_id")
    private SecUser secUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pay_plan_id")
    private PayPlan payPlan;

    @Column(name = "actual_amount_paid")
    private Double actualAmountPaid;

    @Column(name = "actual_amount_paid_cent")
    private Double actualAmountPaidCent;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus paymentStatus;

    @Column(name = "transaction_id")
    private String transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Lob
    @Column(name = "stripe_response")
    private String stripeResponse;

    @Column(name = "stripe_charge_id")
    private String stripeChargeId;

    @Column(name = "stripe_balance_transaction_id")
    private String stripeBalanceTransactionId;

    @Column(name = "stripe_failure_message")
    private String stripeFailureMessage;

    @Column(name = "stripe_failure_code")
    private String stripeFailureCode;

    @Column(name = "payment_note")
    private String paymentNote;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_service_provider")
    private PaymentServiceProvider paymentServiceProvider;

    @Column(name = "payment_card_type")
    private String paymentCardType;

    @Column(name = "card_last_four")
    private String cardLastFour;

    @Column(name = "card_bin")
    private String cardBin;

    @Column(name = "card_token")
    private String cardToken;

    @Lob
    @Column(name = "card_object")
    private String cardObject;

    @Column(name = "payment_transaction_id")
    private String paymentTransactionId;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "payment_amount")
    private String paymentAmount;

    @Column(name = "card_payment_status")
    private String cardPaymentStatus;

    @Column(name = "payment_created_date")
    private Instant paymentCreatedDate;

    @Column(name = "payment_updated_date")
    private Instant paymentUpdatedDate;

    @Lob
    @Column(name = "payment_object")
    private String paymentObject;
}


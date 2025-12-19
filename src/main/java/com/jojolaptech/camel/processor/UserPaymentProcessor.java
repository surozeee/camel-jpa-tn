package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.enums.PaidStatus;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
import com.jojolaptech.camel.model.postgres.enums.DiscountTypeEnum;
import com.jojolaptech.camel.model.postgres.enums.PaymentModeEnum;
import com.jojolaptech.camel.model.postgres.enums.TransactionStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.mysql.tendersystem.PayPlan;
import com.jojolaptech.camel.model.postgres.notice.PaymentRuleEntity;
import com.jojolaptech.camel.model.postgres.notice.TransactionEntity;
import com.jojolaptech.camel.repository.mysql.PayPlanRepository;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
import com.jojolaptech.camel.repository.postgres.notice.PaymentRuleRepository;
import com.jojolaptech.camel.repository.postgres.notice.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserPaymentProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(UserPaymentProcessor.class);

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final PaymentRuleRepository paymentRuleRepository;
    private final PayPlanRepository payPlanRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        UserPayment source = exchange.getMessage().getMandatoryBody(UserPayment.class);

        log.info("Migrating user_payment id={}, transactionId={}", source.getId(), source.getTransactionId());

        // Check if already exists
        if (transactionRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping user_payment id={}, already exists", source.getId());
            return;
        }

        // Look up the user by mysqlId
        UUID userId = null;
        if (source.getSecUser() != null && source.getSecUser().getId() != null) {
            Optional<UserEntity> userOpt = userRepository.findByMysqlId(source.getSecUser().getId());
            
            if (userOpt.isPresent()) {
                userId = userOpt.get().getId();
                log.debug("Found user with mysqlId={}, postgresId={}", source.getSecUser().getId(), userId);
            } else {
                log.warn("User not found in Postgres for mysqlId={}", source.getSecUser().getId());
            }
        }

        // Get PayPlan - should be eagerly loaded via JOIN FETCH, but fetch separately as fallback
        PayPlan payPlan = null;
        Long payPlanId = null;
        
        try {
            // Try to access PayPlan directly (should work if eagerly loaded)
            if (source.getPayPlan() != null) {
                payPlan = source.getPayPlan();
                payPlanId = payPlan.getId();
            }
        } catch (org.hibernate.LazyInitializationException e) {
            // If lazy loading exception, fetch separately
            log.debug("PayPlan not eagerly loaded, fetching separately");
            // Try to get ID from the proxy if possible
            try {
                payPlanId = source.getPayPlan().getId();
            } catch (Exception ex) {
                log.warn("Could not access PayPlan ID, skipping PayPlan-related mappings");
            }
        }
        
        // Fetch PayPlan separately if not already loaded
        if (payPlan == null && payPlanId != null) {
            Optional<PayPlan> payPlanOpt = payPlanRepository.findById(payPlanId);
            if (payPlanOpt.isPresent()) {
                payPlan = payPlanOpt.get();
            }
        }
        
        // Look up the payment rule by PayPlan mysqlId
        UUID paymentRuleId = null;
        if (payPlanId != null) {
            Optional<PaymentRuleEntity> paymentRuleOpt = paymentRuleRepository.findByMysqlId(payPlanId);
            
            if (paymentRuleOpt.isPresent()) {
                paymentRuleId = paymentRuleOpt.get().getId();
                log.debug("Found payment rule with PayPlan mysqlId={}, postgresId={}", payPlanId, paymentRuleId);
            } else {
                log.warn("Payment rule not found in Postgres for PayPlan mysqlId={}", payPlanId);
            }
        }

        TransactionEntity target = new TransactionEntity();
        target.setMysqlId(source.getId());
        target.setReferenceNumber(source.getTransactionId());
        
        // Map payType string to PaymentModeEnum
        if (source.getPayType() != null && !source.getPayType().trim().isEmpty()) {
            String payTypeTrimmed = source.getPayType().trim();
            PaymentModeEnum paymentMode = null;
            
            // Special case: 't' or 'T' means TRIAL
            if (payTypeTrimmed.equalsIgnoreCase("t")) {
                paymentMode = PaymentModeEnum.TRIAL;
            } else {
                String payTypeUpper = payTypeTrimmed.toUpperCase();
                try {
                    paymentMode = PaymentModeEnum.valueOf(payTypeUpper);
                } catch (IllegalArgumentException e) {
                    // Try to find by key
                    for (PaymentModeEnum mode : PaymentModeEnum.values()) {
                        if (mode.getKey().equalsIgnoreCase(payTypeUpper)) {
                            paymentMode = mode;
                            break;
                        }
                    }
                    if (paymentMode == null) {
                        paymentMode = PaymentModeEnum.BANK_DEPOSIT;
                        log.warn("Unknown payment mode: {}, setting to BANK_DEPOSIT", source.getPayType());
                    }
                }
            }
            target.setPaymentMode(paymentMode);
        }
        
        // Map grossAmount - use PayPlan.money (original price before discount)
        if (payPlan != null && payPlan.getMoney() > 0) {
            target.setGrossAmount(BigDecimal.valueOf(payPlan.getMoney()));
        } else if (source.getNetPayPlan() > 0) {
            // If PayPlan is not available, use netPayPlan as grossAmount (fallback)
            target.setGrossAmount(BigDecimal.valueOf(source.getNetPayPlan()));
        } else {
            target.setGrossAmount(BigDecimal.ZERO);
        }
        
        // Map discount - prefer discountPercent from UserPayment, fallback to PayPlan.discount
        if (source.getDiscountPercent() != null && source.getDiscountPercent() > 0) {
            target.setDiscount(BigDecimal.valueOf(source.getDiscountPercent()));
            target.setDiscountType(DiscountTypeEnum.PERCENT);
        } else if (payPlan != null && payPlan.getDiscount() > 0) {
            target.setDiscount(BigDecimal.valueOf(payPlan.getDiscount()));
            target.setDiscountType(DiscountTypeEnum.PERCENT);
        } else {
            target.setDiscount(BigDecimal.ZERO);
            target.setDiscountType(DiscountTypeEnum.FLAT);
        }
        
        // Map paidAmount (netAmountPaid) - prefer toPayAmount, fallback to netPayPlan
        if (source.getToPayAmount() != null) {
            target.setNetAmountPaid(BigDecimal.valueOf(source.getToPayAmount()));
        } else {
            target.setNetAmountPaid(BigDecimal.valueOf(source.getNetPayPlan()));
        }
        
        // Convert Instant to LocalDate for paidDate (paymentDate)
        if (source.getPayDate() != null) {
            LocalDate paymentDate = source.getPayDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            target.setPaymentDate(paymentDate);
        }
        
        // Convert Instant to LocalDate for expiryDate
        if (source.getExpireDate() != null) {
            LocalDate expiryDate = source.getExpireDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            target.setExpiryDate(expiryDate);
        } else {
            // Explicitly set to null if not present
            target.setExpiryDate(null);
        }

        target.setNote(source.getPaymentNote());
        target.setReceipt(source.getVoucherChequeId());
        
        // Map PaidStatus to TransactionStatusEnum
        if (source.getPaidStatus() != null) {
            switch (source.getPaidStatus()) {
                case Pending:
                    target.setStatus(TransactionStatusEnum.VERIFICATION_PENDING);
                    break;
                case Verify:
                    target.setStatus(TransactionStatusEnum.APPROVED);
                    break;
                case Rejected:
                case Deleted:
                    target.setStatus(TransactionStatusEnum.REJECTED);
                    break;
                default:
                    target.setStatus(TransactionStatusEnum.VERIFICATION_PENDING);
            }
        } else {
            target.setStatus(TransactionStatusEnum.VERIFICATION_PENDING);
        }
        
        // Set approveDate (approveRejectDate) from verifyDate when status is Verify or Rejected
        if (source.getVerifyDate() != null && 
            (source.getPaidStatus() == PaidStatus.Verify || 
             source.getPaidStatus() == PaidStatus.Rejected || 
             source.getPaidStatus() == PaidStatus.Deleted)) {
            LocalDate approveRejectDate = source.getVerifyDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            target.setApproveRejectDate(approveRejectDate);
        } else {
            // Explicitly set to null if not applicable
            target.setApproveRejectDate(null);
        }
        
        target.setUserId(userId);
        target.setPaymentRuleId(paymentRuleId);
        
        // Set reject reason if status is rejected
        if (target.getStatus() == TransactionStatusEnum.REJECTED && source.getPaidStatus() == PaidStatus.Rejected) {
            target.setRejectReason("Payment rejected");
        }

        TransactionEntity saved = transactionRepository.save(target);
        log.info("Saved transaction to Postgres with id={}, referenceNumber={}", saved.getId(), saved.getReferenceNumber());
    }
}


package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.enums.PaidStatus;
import com.jojolaptech.camel.model.mysql.tendersystem.UserPayment;
import com.jojolaptech.camel.model.postgres.enums.PaymentModeEnum;
import com.jojolaptech.camel.model.postgres.enums.TransactionStatusEnum;
import com.jojolaptech.camel.model.postgres.iam.UserEntity;
import com.jojolaptech.camel.model.postgres.notice.TransactionEntity;
import com.jojolaptech.camel.repository.postgres.iam.UserRepository;
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

        TransactionEntity target = new TransactionEntity();
        target.setMysqlId(source.getId());
        target.setReferenceNumber(source.getTransactionId());
        
        // Map payType string to PaymentModeEnum
        if (source.getPayType() != null && !source.getPayType().trim().isEmpty()) {
            String payTypeUpper = source.getPayType().toUpperCase().trim();
            PaymentModeEnum paymentMode = null;
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
                    log.warn("Unknown payment mode: {}, setting to null", source.getPayType());
                }
            }
            target.setPaymentMode(paymentMode);
        }
        
        // Map amount - prefer toPayAmount, fallback to netPayPlan
        if (source.getToPayAmount() != null) {
            target.setAmount(BigDecimal.valueOf(source.getToPayAmount()));
        } else if (source.getNetPayPlan() > 0) {
            target.setAmount(BigDecimal.valueOf(source.getNetPayPlan()));
        }
        
        // Convert Instant to LocalDate
        if (source.getPayDate() != null) {
            LocalDate paymentDate = source.getPayDate()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
            target.setPaymentDate(paymentDate);
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
        
        target.setUserId(userId);
        
        // Set reject reason if status is rejected
        if (target.getStatus() == TransactionStatusEnum.REJECTED && source.getPaidStatus() == PaidStatus.Rejected) {
            target.setRejectReason("Payment rejected");
        }

        TransactionEntity saved = transactionRepository.save(target);
        log.info("Saved transaction to Postgres with id={}, referenceNumber={}", saved.getId(), saved.getReferenceNumber());
    }
}


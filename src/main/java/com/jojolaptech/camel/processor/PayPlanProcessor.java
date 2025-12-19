package com.jojolaptech.camel.processor;

import com.jojolaptech.camel.model.mysql.tendersystem.PayPlan;
import com.jojolaptech.camel.model.postgres.enums.ValueTypeEnum;
import com.jojolaptech.camel.model.postgres.notice.PaymentRuleEntity;
import com.jojolaptech.camel.repository.postgres.notice.PaymentRuleRepository;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.InvalidPayloadException;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class PayPlanProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(PayPlanProcessor.class);
    private static final Pattern PERIOD_PATTERN = Pattern.compile("(\\d+)");

    private final PaymentRuleRepository paymentRuleRepository;

    @Override
    public void process(Exchange exchange) throws InvalidPayloadException {
        PayPlan source = exchange.getMessage().getMandatoryBody(PayPlan.class);

        log.info("Migrating pay_plan id={}, period={}, money={}", source.getId(), source.getPeriod(), source.getMoney());

        // Check if already exists by mysqlId
        if (paymentRuleRepository.existsByMysqlId(source.getId())) {
            log.info("Skipping pay_plan id={}, already exists by mysqlId", source.getId());
            return;
        }

        PaymentRuleEntity target = new PaymentRuleEntity();
        target.setMysqlId(source.getId());
        
        // Parse period string to extract number of months
        int periodInMonth = parsePeriodToMonths(source.getPeriod());
        target.setPeriodInMonth(periodInMonth);
        
        // Convert long to BigDecimal
        target.setTotalPrice(BigDecimal.valueOf(source.getMoney()));
        target.setNetPrice(BigDecimal.valueOf(source.getNet()));
        target.setDiscount(BigDecimal.valueOf(source.getDiscount()));
        
        // Determine valueType: if discount is typically a percentage, use PERCENTAGE
        // Since discount is an int in PayPlan, we'll assume it's a percentage
        target.setValueType(ValueTypeEnum.PERCENTAGE);
        
        target.setVersion(0L);

        PaymentRuleEntity saved = paymentRuleRepository.save(target);
        log.info("Saved payment_rule to Postgres with id={}, mysqlId={}, periodInMonth={}", 
                saved.getId(), saved.getMysqlId(), saved.getPeriodInMonth());
    }

    /**
     * Parse period string (e.g., "1 month", "3 months", "6 months", "12 months") to integer months
     */
    private int parsePeriodToMonths(String period) {
        if (period == null || period.trim().isEmpty()) {
            log.warn("Period is null or empty, defaulting to 1 month");
            return 1;
        }
        
        Matcher matcher = PERIOD_PATTERN.matcher(period);
        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group(1));
            } catch (NumberFormatException e) {
                log.warn("Failed to parse period '{}', defaulting to 1 month", period);
                return 1;
            }
        }
        
        log.warn("No number found in period '{}', defaulting to 1 month", period);
        return 1;
    }
}


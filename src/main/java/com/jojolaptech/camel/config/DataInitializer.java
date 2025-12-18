package com.jojolaptech.camel.config;

import com.jojolaptech.camel.model.postgres.enums.PaymentModeEnum;
import com.jojolaptech.camel.model.postgres.enums.ScopeEnum;
import com.jojolaptech.camel.model.postgres.enums.StatusEnum;
import com.jojolaptech.camel.model.postgres.iam.RoleEntity;
import com.jojolaptech.camel.model.postgres.iam.ScopeEntity;
import com.jojolaptech.camel.model.postgres.notice.CurrencyEntity;
import com.jojolaptech.camel.model.postgres.notice.PaymentModeEntity;
import com.jojolaptech.camel.repository.postgres.iam.RoleRepository;
import com.jojolaptech.camel.repository.postgres.iam.ScopeRepository;
import com.jojolaptech.camel.repository.postgres.notice.CurrencyRepository;
import com.jojolaptech.camel.repository.postgres.notice.PaymentModeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    private final RoleRepository roleRepository;
    private final ScopeRepository scopeRepository;
    private final CurrencyRepository currencyRepository;
    private final PaymentModeRepository paymentModeRepository;

    @Override
    @Transactional("postgresTransactionManager")
    public void run(String... args) {
        log.info("Starting data initialization...");
        
        initializeRoles();
        initializePaymentModes();
        
        log.info("Data initialization completed.");
    }

    private void initializeRoles() {
        log.info("Initializing roles...");
        createRoleIfNotExists("SUPER_ADMIN");
        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("CUSTOMER");
        createRoleIfNotExists("PARTNER");
        createRoleIfNotExists("ACCOUNTANT");
        
        log.info("Roles initialization completed. Total roles: {}", roleRepository.count());
    }

    private void createRoleIfNotExists(String roleName) {
        if (!roleRepository.existsByName(roleName)) {
            RoleEntity role = RoleEntity.builder()
                    .name(roleName)
                    .status(StatusEnum.ACTIVE)
                    .build();
            roleRepository.save(role);
            log.info("Created role: {}", roleName);
        }
    }

    private void initializeScopes() {
        log.info("Initializing scopes...");
        
        for (ScopeEnum scopeEnum : ScopeEnum.values()) {
            createScopeIfNotExists(scopeEnum.name());
        }
        
        log.info("Scopes initialization completed. Total scopes: {}", scopeRepository.count());
    }

    private void createScopeIfNotExists(String scopeName) {
        if (!scopeRepository.existsByName(scopeName)) {
            ScopeEntity scope = ScopeEntity.builder()
                    .name(scopeName)
                    .status(StatusEnum.ACTIVE)
                    .build();
            scopeRepository.save(scope);
            log.info("Created scope: {}", scopeName);
        }
    }

    private void initializeCurrencies() {
        log.info("Initializing currencies...");
        
        createCurrencyIfNotExists("Nepalese Rupee", "NPR", "रू");
        createCurrencyIfNotExists("US Dollar", "USD", "$");
        createCurrencyIfNotExists("Indian Rupee", "INR", "₹");
        createCurrencyIfNotExists("Euro", "EUR", "€");
        createCurrencyIfNotExists("British Pound", "GBP", "£");
        
        log.info("Currencies initialization completed. Total currencies: {}", currencyRepository.count());
    }

    private void createCurrencyIfNotExists(String name, String code, String symbol) {
        if (!currencyRepository.existsByCode(code)) {
            CurrencyEntity currency = CurrencyEntity.builder()
                    .name(name)
                    .code(code)
                    .symbol(symbol)
                    .status(StatusEnum.ACTIVE)
                    .build();
            currencyRepository.save(currency);
            log.info("Created currency: {} ({})", name, code);
        }
    }

    private void initializePaymentModes() {
        log.info("Initializing payment modes...");
        
        for (PaymentModeEnum paymentModeEnum : PaymentModeEnum.values()) {
            createPaymentModeIfNotExists(paymentModeEnum);
        }
        
        log.info("Payment modes initialization completed. Total payment modes: {}", paymentModeRepository.count());
    }

    private void createPaymentModeIfNotExists(PaymentModeEnum paymentModeEnum) {
        if (!paymentModeRepository.existsByPaymentMode(paymentModeEnum)) {
            String displayName = formatPaymentModeName(paymentModeEnum.name());
            PaymentModeEntity paymentMode = PaymentModeEntity.builder()
                    .name(displayName)
                    .paymentMode(paymentModeEnum)
                    .status(StatusEnum.ACTIVE)
                    .build();
            paymentModeRepository.save(paymentMode);
            log.info("Created payment mode: {}", displayName);
        }
    }

    private String formatPaymentModeName(String enumName) {
        // Convert BANK_TRANSFER to "Bank Transfer"
        String[] words = enumName.toLowerCase().split("_");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!result.isEmpty()) {
                result.append(" ");
            }
            result.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
        }
        return result.toString();
    }
}


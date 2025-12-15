package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;

@Getter
public enum PaymentModeEnum {

    BANK_TRANSFER("BANK_TRANSFER"),
    BANK_DEPOSIT("BANK_DEPOSIT"),
    WALLET_TRANSFER("WALLET_TRANSFER"),
    ESEWA("ESEWA"),
    KHALTI("KHALTI"),
    CONNECT_IPS("CONNECT_IPS"),
    CASH("CASH");

    private final String key;

    PaymentModeEnum(String key) {
        this.key = key;
    }
}


package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;

@Getter
public enum DiscountTypeEnum {
    FLAT("Flat"),
    PERCENT("Percent");

    private final String type;

    DiscountTypeEnum(String type) {
        this.type = type;
    }
}

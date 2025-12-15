package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;

@Getter
public enum DateFormatEnum {

    BS("BS", "Nepali (B.S)"),
    AD("AD", "English (A.D");

    private final String name;
    private final String description;

    DateFormatEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

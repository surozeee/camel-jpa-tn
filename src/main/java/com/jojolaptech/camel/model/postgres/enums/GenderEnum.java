package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;

@Getter
public enum GenderEnum {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");
    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }
}

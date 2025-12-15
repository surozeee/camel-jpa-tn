package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;


@Getter
public enum UserStatusEnum {

    PENDING("Pending", "User status is pending for approval or verification"),
    ACTIVE("Active", "User status is active after approval or verification"),
    DISABLED("Disabled", "User status is disabled when user inactive."),
    BLOCKED("Blocked", "User status is blocked when user is blocked"),
    LOCKED("Locked", "User status is locked when user is locked."),
    DELETED("Deleted", "User status is deleted when user is deleted.");

    private final String name;
    private final String description;

    UserStatusEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

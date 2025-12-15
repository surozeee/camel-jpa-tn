package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;


@Getter
public enum TenderStatus {

    PENDING("Pending", "Tender is pending and awaiting action"),
    PUBLISHED("Published", "Tender is published and open for bidding"),
    CLOSED("Closed", "Tender is closed and no longer accepting bids"),
    CANCELLED("Cancelled", "Tender has been cancelled");

    private final String displayName;
    private final String description;

    TenderStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
}

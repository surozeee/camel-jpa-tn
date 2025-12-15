package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;


@Getter
public enum ScopeEnum {

    READ("Read", "Viewing application data, such as users, roles, and permissions."),
    WRITE("Write", "Adding data to the application, such as users, roles, and permissions."),
    DELETE("Delete", "Deleting data from the application, such as users, roles, and permissions."),
    UPDATE("Update", "Updating data in the application, such as users, roles, and permissions."),
    SUPER_ADMIN("Super Admin", "Full access to the application for super admins."),
    APPROVE("Approve", "Approving data in the application."),
    REJECT("Reject", "Rejecting data in the application."),
    ASSIGN("Assign", "Assigning data in the application."),
    CHANGE_STATUS("Change Status", "Changing the status of data in the application."),
    EXPORT("Export", "Exporting data from the application."),
    IMPORT("Import", "Importing data into the application."),
    UPLOAD("Upload", "Uploading files to the application."),
    FULL_ACCESS("Full Access", "Full access to the application for super admins.");

    private final String name;
    private final String description;

    ScopeEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }
}

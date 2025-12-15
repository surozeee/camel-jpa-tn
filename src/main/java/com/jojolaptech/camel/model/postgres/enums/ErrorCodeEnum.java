package com.jojolaptech.camel.model.postgres.enums;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

    ERR001("ERR001", "An unexpected error occurred. If you continue experiencing issues, please reach out to our support team."),
    ERR002("ERR002", "Invalid input. Check the following fields."),
    SER001("SER001", "The service is currently unavailable. Please try again later."),
    BUC001("BUC001", "Document is not valid"),
    BUC002("BUC002", "Document size is too large. Max size: {0}"),
    BUC003("BUC003", "Document type is not valid. Allowed types: {0}"),
    BUC004("BUC004", "Failed to upload document"),
    BUC005("BUC005", "Failed to delete document"),
    BUC006("BUC006", "Document not found"),
    BUC008("BUC008", "Bucket creation failed."),
    OTP001("OTP001", "OTP length must be provided and must be between 4-8 digits."),
    OTP002("OTP002", "OTP must not be empty & must be {0} digits."),
    OTP003("OTP003", "OTP is not valid."),
    OTP004("OTP004", "OTP is expired."),
    TEM001("TEM001", "Template should be unique for each toptopic, language and channel."),
    TEM002("TEM002", "Template does not exist."),
    TEM003("TEM003", "SMS Template doesn't required subject."),
    NOT001("NOT001", "An error occurred in kafka when sending notification."),
    NOT002("NOT002", "Notification not found."),
    CSV001("CSV001", "CSV file doesn't exist."),
    CSV002("CSV002", "CSV file cannot be parse."),
    EMA001("EMA001", "Email not sent."),
    ADB001("ADB001", "Advertisement banner not found."),
    AES001("AES001", "Failed to encrypt data using AES."),
    AES002("AES002", "Failed to decrypt data using AES."),
    ACT001("ACT001", "Activity log not found."),
    IAM005("IAM005", "You don't have sufficient permissions to access this resource."),
    CNO001("CNO001", "Custom notification not found."),
    ATC001("ATC001", "Attachment is invalid might be corrupted."),
    ATC002("ATC002", "File size is too large it support {0} MB"),
    ATC003("ATC003", "Total file size is too large."),
    ATC004("ATC004", "Invalid file content type. allowed file content types is: {0}"),
    ATC005("ATC005", "Failed to download attachment from url"),

    ;

    private final String code;
    private final String message;

    ErrorCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

package com.priyajafari.multithreadproject.Validation;


import lombok.Getter;

@Getter
public enum ValidationError {

    INVALID_ACCOUNT_BALANCE("VAL001", "Account Validation", "Account balance exceeds the account limit."),
    INVALID_ACCOUNT_TYPE("VAL002", "Account Validation", "Invalid account type."),
    INVALID_CUSTOMER_BIRTH_DATE("VAL003", "Customer Validation", "Customer's age is not legal."),
    INVALID_ACCOUNT_NUMBER("VAL004", "Account Validation", "Invalid account number format."),
    INVALID_ACCOUNT_CUSTOMER_ID("VAL005", "Account Customer Matching Validation", "Invalid customer ID format."),
    INVALID_CUSTOMER_NATIONAL_ID("VAL006", "Customer Validation", "Invalid customer national ID format."),
    INVALID_FIELD_VALUE("VAL007", "Not Null Validation", "Field value is null.");

    private final String errorCode;
    private final String classificationName;
    private final String errorDescription;

    ValidationError(String errorCode, String classificationName, String errorDescription) {
        this.errorCode = errorCode;
        this.classificationName = classificationName;
        this.errorDescription = errorDescription;
    }

}

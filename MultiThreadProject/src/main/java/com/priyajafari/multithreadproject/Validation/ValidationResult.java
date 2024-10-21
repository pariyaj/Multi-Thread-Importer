package com.priyajafari.multithreadproject.Validation;

public class ValidationResult {

    private boolean valid;
    private ValidationError error;

    public ValidationResult(boolean valid, ValidationError error) {
        this.valid = valid;
        this.error = error;
    }

    public boolean isValid() {
        return valid;
    }

    public ValidationError getError() {
        return error;
    }

    public String getErrorMessage() {
        return error != null ? error.getErrorDescription() : "No error description";
    }

    public String getErrorCode() {
        return error != null ? error.getClassificationName() : "No error code";
    }

}

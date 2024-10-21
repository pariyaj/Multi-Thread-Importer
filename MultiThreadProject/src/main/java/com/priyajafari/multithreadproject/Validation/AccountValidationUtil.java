package com.priyajafari.multithreadproject.Validation;

import com.priyajafari.multithreadproject.model.Account;
import com.priyajafari.multithreadproject.model.AccountType;
import com.priyajafari.multithreadproject.model.AccountTypeSubSetValidator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class AccountValidationUtil {

    @Autowired
    private Account account;
    @Autowired
    private AccountType accountType;
    @Autowired
    private Validator validator;
    @Autowired
    private AccountTypeSubSetValidator accountTypeSubSetValidator;


    @SneakyThrows
    public ValidationResult validateAccountData() {
        // an array list to save validation errors
        List<ValidationError> accountErrors = new ArrayList<>();

        // method for different validations of customer data
        performValidation(this::validateAccountBalance, accountErrors);
        performValidation(() -> {
            try {
                return validateAccountBalance();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_BALANCE);
            }}, accountErrors);

        performValidation(this::validateAccountType, accountErrors);
        performValidation(() -> {
            try {
                return validateAccountType(accountType);
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_TYPE);
            }}, accountErrors);

        performValidation(this::validateAccountNumber, accountErrors);
        performValidation(() -> {
            try {
                return validateAccountNumber();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_NUMBER);
            }}, accountErrors);

        performValidation(this::validateAccountFields, accountErrors);
        performValidation(() -> {
            try {
                return validateAccountFields();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_FIELD_VALUE);
            }}, accountErrors);

        // when any error validation hs been occurred
        if (!accountErrors.isEmpty()) {
            return (ValidationResult) accountErrors;
        }

        // when all validations pass
        return new ValidationResult(true, null);
    }
    // to perform all validations for customer at once
    public void performValidation(Supplier<ValidationResult> validationMethod, List<ValidationError> errors) {
        ValidationResult result = validationMethod.get();
        if (!result.isValid()) {
            errors.add(result.getError());
        }
    }

    public ValidationResult validateAccountBalance() throws Exception {

        String decryptedAccountBalance = AESEncryption.decrypt(account.getAccountBalance());
        double balance = Double.parseDouble(decryptedAccountBalance);
        if ( balance <= account.getAccountLimit()) {
            return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_BALANCE);
        }
        return new ValidationResult(true, null);

    }

    public ValidationResult validateAccountType(AccountType accountType) throws Exception {

        if (!accountTypeSubSetValidator.isValid(accountType, null))
        {
            return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_TYPE);
        }
        return new ValidationResult(true, null);
    }

    public ValidationResult validateAccountNumber() throws Exception {

        String decryptedAccountNumber = AESEncryption.decrypt(account.getAccountNumber());
        if (decryptedAccountNumber.length() != 22 || !decryptedAccountNumber.matches("\\d{22}")) {
            return new ValidationResult(false, ValidationError.INVALID_ACCOUNT_NUMBER);
        }
        return new ValidationResult(true, null);

    }

    public ValidationResult validateAccountFields () {

        Set<ConstraintViolation<Account>> violations = validator.validate(account);
        if (!violations.isEmpty()) {
            return new ValidationResult(false, ValidationError.INVALID_FIELD_VALUE);
        }
        return new ValidationResult(true, null);
    }

}

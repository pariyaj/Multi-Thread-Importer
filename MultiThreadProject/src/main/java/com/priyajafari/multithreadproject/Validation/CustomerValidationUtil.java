package com.priyajafari.multithreadproject.Validation;

import com.priyajafari.multithreadproject.model.Customer;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@Component
public class CustomerValidationUtil {

    private Customer customer;

    private Validator validator;

    @SneakyThrows
    public ValidationResult validateCustomerData() {
        // an array list to save validation errors
        List<ValidationError> customerErrors = new ArrayList<>();

        // methods for different validations of customer data
        performValidation(this::validateCustomerBirthDate, customerErrors);
        performValidation(() -> {
            try {
                return validateCustomerBirthDate();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_CUSTOMER_BIRTH_DATE);
            }
        }, customerErrors);

        performValidation(this::validateCustomerFields, customerErrors);
        performValidation(() -> {
            try {
                return validateCustomerFields();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_FIELD_VALUE);
            }
        }, customerErrors);

        performValidation(this::validateCustomerNationalId, customerErrors);
        performValidation(() -> {
            try {
                return validateCustomerNationalId();
            } catch (Exception e) {
                return new ValidationResult(false, ValidationError.INVALID_CUSTOMER_NATIONAL_ID);
            }
        }, customerErrors);

        // when any error validation hs been occurred
        if (!customerErrors.isEmpty()) {
            return (ValidationResult) customerErrors;
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

    //validate customer birthdate
    public ValidationResult validateCustomerBirthDate () {
        Date birthDate = customer.getCustomerBirthDate();
        LocalDate birthLocalDate = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if ( birthLocalDate.getYear() > 1995 ) {
            return new ValidationResult(false, ValidationError.INVALID_CUSTOMER_BIRTH_DATE);
        }
        return new ValidationResult(true,null);
    }
   //validate customer national id
    public ValidationResult validateCustomerNationalId () throws Exception {

        String decryptedId = AESEncryption.decrypt(customer.getCustomerNationalId());
        if ( (decryptedId.length() != 10 || !decryptedId.matches("\\d{10}")) ) {
            return new ValidationResult(false, ValidationError.INVALID_CUSTOMER_NATIONAL_ID);
        }
        return new ValidationResult(true,null);
    }
    //validate customer fields for any null value
    public ValidationResult validateCustomerFields () {

        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        if (!violations.isEmpty()) {
            return new ValidationResult(false, ValidationError.INVALID_FIELD_VALUE);
        }
        return new ValidationResult(true,null);
    }

}

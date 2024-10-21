package com.priyajafari.multithreadproject.model;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;


public class AccountTypeSubSetValidator implements ConstraintValidator<AccountTypeSubset, AccountType> {

    private AccountType[] subset;
    private AccountTypeSubset constraint;

    @Override
    public void initialize(AccountTypeSubset constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(AccountType value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}

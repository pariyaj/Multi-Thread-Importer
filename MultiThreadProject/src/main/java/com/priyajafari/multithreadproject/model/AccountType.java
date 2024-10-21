package com.priyajafari.multithreadproject.model;

import lombok.Getter;

@Getter
@AccountTypeSubset(anyOf = {AccountType.SAVINGS, AccountType.RECURRING_DEPOSIT, AccountType.FIXED_DEPOSIT_ACCOUNT})
public enum AccountType {
    SAVINGS(1),
    RECURRING_DEPOSIT(2),
    FIXED_DEPOSIT_ACCOUNT(3);

    private final int value;

    AccountType(final int newValue) {
        this.value = newValue;
    }
}

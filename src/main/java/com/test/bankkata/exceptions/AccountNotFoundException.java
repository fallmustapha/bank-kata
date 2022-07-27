package com.test.bankkata.exceptions;

import java.util.function.Supplier;

public class AccountNotFoundException extends RuntimeException {

    public AccountNotFoundException(String message) {
        super(message);
    }
}

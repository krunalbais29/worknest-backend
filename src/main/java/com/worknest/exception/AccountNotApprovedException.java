package com.worknest.exception;

@SuppressWarnings("serial")
public class AccountNotApprovedException extends RuntimeException {
    public AccountNotApprovedException(String message) {
        super(message);
    }
}
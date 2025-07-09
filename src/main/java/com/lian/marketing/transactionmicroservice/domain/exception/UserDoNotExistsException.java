package com.lian.marketing.transactionmicroservice.domain.exception;

public class UserDoNotExistsException extends RuntimeException {
    public UserDoNotExistsException(String message) {
        super(message);
    }
}

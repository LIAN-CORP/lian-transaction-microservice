package com.lian.marketing.transactionmicroservice.domain.exception;

public class ClientPhoneAlreadyExistsException extends RuntimeException {
    public ClientPhoneAlreadyExistsException(String message) {
        super(message);
    }
}

package com.lian.marketing.transactionmicroservice.domain.exception;

public class ClientPhoneNumberIsNotValid extends RuntimeException {
    public ClientPhoneNumberIsNotValid(String message) {
        super(message);
    }
}

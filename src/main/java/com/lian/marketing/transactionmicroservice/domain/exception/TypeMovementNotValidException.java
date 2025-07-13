package com.lian.marketing.transactionmicroservice.domain.exception;

public class TypeMovementNotValidException extends RuntimeException {
    public TypeMovementNotValidException(String message) {
        super(message);
    }
}

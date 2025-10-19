package com.lian.marketing.transactionmicroservice.domain.exception;

public class PaymentMethodIsRequiredException extends RuntimeException {
  public PaymentMethodIsRequiredException(String message) {
    super(message);
  }
}

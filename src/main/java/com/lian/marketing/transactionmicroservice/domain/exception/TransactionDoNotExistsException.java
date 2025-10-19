package com.lian.marketing.transactionmicroservice.domain.exception;

public class TransactionDoNotExistsException extends RuntimeException {
  public TransactionDoNotExistsException(String message) {
    super(message);
  }
}

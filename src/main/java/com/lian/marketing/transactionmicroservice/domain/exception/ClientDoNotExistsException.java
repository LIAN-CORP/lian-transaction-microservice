package com.lian.marketing.transactionmicroservice.domain.exception;

public class ClientDoNotExistsException extends RuntimeException {
  public ClientDoNotExistsException(String message) {
    super(message);
  }
}

package com.lian.marketing.transactionmicroservice.domain.exception;

public class ErrorCreatingExcelReportException extends RuntimeException {
    public ErrorCreatingExcelReportException(String message) {
        super(message);
    }
}

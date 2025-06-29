package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.exceptionhandler;

import java.time.LocalDateTime;

public record ExceptionResponse (
        String status,
        int code,
        String message,
        LocalDateTime timestamp
) { }

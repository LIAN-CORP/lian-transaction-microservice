package com.lian.marketing.transactionmicroservice.application.dto.request;

import java.util.UUID;

public record CreateTransactionRequest (
        String typeMovement,
        String transactionDate,
        String clientId,
        UUID userId
) {
}

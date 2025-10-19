package com.lian.marketing.transactionmicroservice.domain.model;

import java.math.BigDecimal;
import java.util.UUID;

public record CreditTransaction(
        BigDecimal totalAmount,
        UUID clientId,
        UUID transactionId
        ) {
}

package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record CreateTransactionRequest (
        @NotEmpty(message = ConstantDto.TRANSACTION_TYPE_MOVEMENT_MUST_BE_NOT_EMPTY)
        String typeMovement,
        CreateClientRequest client,
        @NotEmpty(message = ConstantDto.USER_ID_MUST_BE_NOT_EMPTY)
        UUID userId
) {
}

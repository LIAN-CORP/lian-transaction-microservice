package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateTransactionRequest (
        @NotEmpty(message = ConstantDto.TRANSACTION_TYPE_MOVEMENT_MUST_BE_NOT_EMPTY)
        String typeMovement,
        @Valid
        CreateClientRequest client,
        @NotNull(message = ConstantDto.USER_ID_MUST_BE_NOT_EMPTY)
        UUID userId
) {
}

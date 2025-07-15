package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.util.UUID;

public record ProductQuantityRequest(
        @NotEmpty(message = ConstantDto.PRODUCT_ID_MUST_BE_NOT_EMPTY)
        UUID productId,
        @Min(value = 1, message = ConstantDto.PRODUCT_QUANTITY_IS_NOT_VALID)
        Integer quantity
) {
}

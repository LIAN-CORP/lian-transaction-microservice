package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;

public record CompleteCreateTransactionRequest(
        //@Valid
        CreateTransactionRequest transaction,
        @NotEmpty(message = ConstantDto.PRODUCT_LIST_MUST_BE_NOT_EMPTY)
        List<ProductTransactionRequest> products
) {
}

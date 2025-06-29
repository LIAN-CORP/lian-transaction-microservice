package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.constraints.*;

public record CreateClientRequest (
        @NotEmpty(message = ConstantDto.CLIENT_NAME_MUST_BE_NOT_EMPTY)
        String name,

        @NotEmpty(message = ConstantDto.CLIENT_PHONE_MUST_BE_NOT_EMPTY)
        @Min(value = ConstantDto.MIN_CLIENT_PHONE_LENGTH, message = ConstantDto.CLIENT_PHONE_MESSAGE)
        @Max(value = ConstantDto.MAX_CLIENT_PHONE_LENGTH, message = ConstantDto.CLIENT_PHONE_MESSAGE)
        @Pattern(regexp = ConstantDto.CLIENT_PHONE_REGEX)
        String phone
) {}

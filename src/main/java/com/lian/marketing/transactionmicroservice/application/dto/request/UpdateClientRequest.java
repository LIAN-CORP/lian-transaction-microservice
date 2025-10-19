package com.lian.marketing.transactionmicroservice.application.dto.request;

import com.lian.marketing.transactionmicroservice.application.constants.ConstantDto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateClientRequest(
  @NotNull(message = ConstantDto.CLIENT_ID_MUST_BE_NOT_EMPTY)
  UUID id,
  @NotEmpty(message = ConstantDto.CLIENT_PHONE_MUST_BE_NOT_EMPTY)
  String phone,
  @NotEmpty(message = ConstantDto.CLIENT_NAME_MUST_BE_NOT_EMPTY)
  String name
) {
}

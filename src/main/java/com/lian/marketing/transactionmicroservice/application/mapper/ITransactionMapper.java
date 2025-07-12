package com.lian.marketing.transactionmicroservice.application.mapper;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ITransactionMapper {
    Transaction toModel(CreateTransactionRequest createTransactionRequest);
}

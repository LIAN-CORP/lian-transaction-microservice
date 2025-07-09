package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper;

import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITransactionEntityMapper {
    @Mapping(
        target = "clientId",
        expression = "java( transaction.getClient().getId() )"
    )
    TransactionEntity toEntity(Transaction transaction);
}

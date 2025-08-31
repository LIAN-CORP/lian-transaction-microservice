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
    @Mapping(target = "typeMovement", expression = "java( transaction.getTypeMovement().name())")
    TransactionEntity toEntity(Transaction transaction);

    @Mapping(target = "client.id", expression = "java(transactionEntity.getClientId())")
    @Mapping(target = "typeMovement", expression = "java(type_movement.valueOf(transactionEntity.getTypeMovement()))")
    Transaction toModel(TransactionEntity transactionEntity);
}

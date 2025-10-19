package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.DetailTransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDetailTransactionEntityMapper {
    DetailTransactionEntity toEntityFromModel(DetailTransaction detailTransaction);
    DetailTransaction toModelFromEntity(DetailTransactionEntity detailTransactionEntity);
}

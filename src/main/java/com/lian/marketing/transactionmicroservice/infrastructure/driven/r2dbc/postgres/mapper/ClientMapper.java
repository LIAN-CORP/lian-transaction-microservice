package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {
    ClientEntity toEntity(Client client);
    Client toModel(ClientEntity clientEntity);
}

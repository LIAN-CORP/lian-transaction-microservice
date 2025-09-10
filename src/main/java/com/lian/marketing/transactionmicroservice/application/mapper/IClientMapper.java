package com.lian.marketing.transactionmicroservice.application.mapper;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.dto.request.UpdateClientRequest;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IClientMapper {
    @Mapping(ignore = true, target = "id")
    Client toModel(CreateClientRequest createClientRequest);
    Client toModelFromUpdate(UpdateClientRequest updateClientRequest);
}

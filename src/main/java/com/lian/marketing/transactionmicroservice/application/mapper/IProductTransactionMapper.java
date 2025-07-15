package com.lian.marketing.transactionmicroservice.application.mapper;

import com.lian.marketing.transactionmicroservice.application.dto.request.ProductTransactionRequest;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IProductTransactionMapper {
    @Mapping(target = "priceSell", ignore = true)
    ProductTransaction toModelFromRequest(ProductTransactionRequest productTransactionRequest);
}

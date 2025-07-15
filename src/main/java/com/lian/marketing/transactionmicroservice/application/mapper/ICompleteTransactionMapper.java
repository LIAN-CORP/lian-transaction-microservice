package com.lian.marketing.transactionmicroservice.application.mapper;

import com.lian.marketing.transactionmicroservice.application.dto.request.CompleteCreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.domain.model.CompleteTransaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ITransactionMapper.class, IProductTransactionMapper.class})
public interface ICompleteTransactionMapper {
    CompleteTransaction toModelFromRequest(CompleteCreateTransactionRequest completeCreateTransactionRequest);
}

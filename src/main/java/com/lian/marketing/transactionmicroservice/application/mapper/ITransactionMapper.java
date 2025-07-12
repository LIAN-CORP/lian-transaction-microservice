package com.lian.marketing.transactionmicroservice.application.mapper;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.TypeMovementNotValidException;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.model.type_movement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {IClientMapper.class})
public interface ITransactionMapper {
    @Mapping(ignore = true, target = "id")
    @Mapping(source = "typeMovement", target = "typeMovement", qualifiedByName = "stringToTypeMovementEnum")
    Transaction toModelFromRequest(CreateTransactionRequest createTransactionRequest);

    @Named("stringToTypeMovementEnum")
    default type_movement stringToTypeMovementEnum(String typeMovement) {
        try {
            return type_movement.valueOf(typeMovement.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new TypeMovementNotValidException(String.format(GeneralConstants.TYPE_MOVEMENT_NOT_VALID, typeMovement));
        }
    }
}

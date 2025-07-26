package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IDetailTransactionPersistencePort {
    Mono<Void> saveDetailTransaction(DetailTransaction detailTransaction);
    Mono<Double> getProductPriceById(UUID productId);
}

package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IDetailTransactionPersistencePort {
    Mono<Void> saveDetailTransaction(DetailTransaction detailTransaction);
    Mono<Double> getProductPriceById(UUID productId);
    Mono<Double> getProductBuyPriceById(UUID productId);
    Mono<Double> findDetailTransactionsByTransactionId(UUID transactionId);
    Flux<DetailTransaction> findAllDetailTransactionsByTransactionId(UUID transactionId);
}

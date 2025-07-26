package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface IDetailTransactionPersistencePort {
    Mono<Void> saveDetailTransaction(DetailTransaction detailTransaction);
    Mono<Double> getProductPriceById(UUID productId);
}

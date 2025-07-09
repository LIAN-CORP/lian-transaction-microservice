package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface ITransactionPersistencePort {
    Mono<Void> saveTransaction(Transaction transaction);
}

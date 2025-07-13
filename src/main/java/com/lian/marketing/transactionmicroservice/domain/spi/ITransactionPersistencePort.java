package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ITransactionPersistencePort {
    Mono<Void> saveTransaction(Transaction transaction);
    Mono<Boolean> userExists(UUID id);
}

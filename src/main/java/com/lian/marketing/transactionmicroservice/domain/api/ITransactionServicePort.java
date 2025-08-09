package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.CompleteTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ITransactionServicePort {
    Mono<UUID> createTransaction(Transaction transaction);
    Mono<Void> createCompleteTransaction(CompleteTransaction completeTransaction);
}

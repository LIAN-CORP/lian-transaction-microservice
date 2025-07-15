package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.CompleteTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface ITransactionServicePort {
    Mono<Void> createTransaction(Transaction transaction);
    Mono<Void> createCompleteTransaction(CompleteTransaction completeTransaction);
}

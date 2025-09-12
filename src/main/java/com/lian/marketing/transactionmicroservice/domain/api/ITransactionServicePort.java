package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.CompleteTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ContentPage;
import com.lian.marketing.transactionmicroservice.domain.model.DebtTransactionExcel;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface ITransactionServicePort {
    Mono<UUID> createTransaction(Transaction transaction);
    Mono<Void> createCompleteTransaction(CompleteTransaction completeTransaction);
    Flux<Transaction> findAllTransactionsByDateRange(LocalDate start, LocalDate end);
    Flux<DebtTransactionExcel> findAllDebtsByDateRange(LocalDate start, LocalDate end);
    Mono<ContentPage<Transaction>> findAllTransactionsByDate(int page, int size, String start, String end);
    Mono<Void> deleteTransactionById(UUID id);
}

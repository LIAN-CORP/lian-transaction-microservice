package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface TransactionRepository extends ReactiveCrudRepository<TransactionEntity, UUID> {
    @Query("SELECT * FROM transactions t INNER JOIN client c on c.id = t.client_id WHERE t.transaction_date BETWEEN :start AND :end")
    Flux<TransactionEntity> findAllByTransactionDateBetween(LocalDate transactionDateAfter, LocalDate transactionDateBefore);

    @Query("SELECT * FROM transactions t INNER JOIN client c on c.id = t.client_id")
    Flux<TransactionEntity> findAll();

    Mono<Long> countByTransactionDateBetween(LocalDate transactionDateAfter, LocalDate transactionDateBefore);

    @Query("SELECT EXISTS(SELECT 1 FROM transactions WHERE type_movement = 'COMPRA' AND id = :id)")
    Mono<Boolean> isBuyTypeTransaction(UUID id);
}

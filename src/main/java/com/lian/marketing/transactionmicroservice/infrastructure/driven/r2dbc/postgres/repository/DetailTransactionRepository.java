package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.DetailTransactionEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DetailTransactionRepository extends ReactiveCrudRepository<DetailTransactionEntity, UUID> {
    Flux<DetailTransactionEntity> findByTransactionId(UUID transactionId);

    @Query("SELECT COALESCE(SUM(unit_price * quantity), 0) FROM detail_transaction WHERE transaction_id = :transactionId")
    Mono<Double> findTotalUnitPriceByTransactionId(UUID transactionId);
}

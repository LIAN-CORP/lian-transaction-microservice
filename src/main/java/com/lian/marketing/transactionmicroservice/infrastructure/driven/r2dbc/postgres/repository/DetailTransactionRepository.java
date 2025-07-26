package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.DetailTransactionEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface DetailTransactionRepository extends ReactiveCrudRepository<DetailTransactionEntity, UUID> {
}

package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.ClientEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, UUID> {
    Mono<ClientEntity> findByPhone(String phone);
    Mono<UUID> findIdByPhone(String phone);
}

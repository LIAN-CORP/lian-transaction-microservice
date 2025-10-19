package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.ClientEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface ClientRepository extends ReactiveCrudRepository<ClientEntity, UUID> {
    Mono<ClientEntity> findByPhone(String phone);
    Mono<UUID> findIdByPhone(String phone);

    @Query("SELECT name FROM client WHERE id = :id")
    Mono<String> findClientNameById(UUID id);

    @Query("SELECT * FROM client WHERE unaccent(name) ILIKE unaccent(CONCAT(:name, '%'))")
    Flux<ClientEntity> findAllByName(String name);
}

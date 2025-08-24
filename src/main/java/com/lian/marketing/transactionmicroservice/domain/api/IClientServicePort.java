package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IClientServicePort {
    Mono<Void> saveClient(Client client);
    Mono<Boolean> existsByPhone(String phone);
    Mono<UUID> findIdByPhone(String phone);
    Mono<UUID> saveClientAndGetId(Client client);
    Mono<Boolean> existsById(UUID id);
}

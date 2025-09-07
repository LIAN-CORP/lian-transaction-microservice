package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IClientPersistencePort {
    Mono<UUID> saveClient(Client client);
    Mono<Client> findClientByPhone(String phone);
    Mono<Boolean> userExists(UUID id);
    Mono<UUID> findIdByPhone(String phone);
    Mono<String> findClientNameById(UUID id);
    Flux<Client> findAllByName(String name);
}

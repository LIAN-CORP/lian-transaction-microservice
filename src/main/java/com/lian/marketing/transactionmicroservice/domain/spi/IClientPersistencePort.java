package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface IClientPersistencePort {
    Mono<Void> saveClient(Client client);
    Mono<Client> findClientByPhone(String phone);
    Mono<Boolean> userExists(UUID id);
}

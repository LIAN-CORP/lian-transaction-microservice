package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import reactor.core.publisher.Mono;

public interface IClientPersistencePort {
    Mono<Void> saveClient(Client client);
    Mono<Client> findClientByPhone(String phone);
}

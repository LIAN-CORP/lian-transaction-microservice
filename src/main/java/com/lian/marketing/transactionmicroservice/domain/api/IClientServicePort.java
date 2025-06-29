package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import reactor.core.publisher.Mono;

public interface IClientServicePort {
    Mono<Void> saveClient(Client client);
}

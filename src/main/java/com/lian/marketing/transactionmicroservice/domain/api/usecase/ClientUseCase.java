package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

    private final IClientPersistencePort clientPersistencePort;

    @Override
    public Mono<Void> saveClient(Client client) {
        if(client.getPhone().length() < 10) {
            client.setPhone(GeneralConstants.COLOMBIA_PREFIX + client.getPhone());
            log.info("Phone number has no prefix, adding prefix to +57 to {}", client.getPhone());
        }
        client.setId(UUID.randomUUID());
        return clientPersistencePort.saveClient(client);
    }
}

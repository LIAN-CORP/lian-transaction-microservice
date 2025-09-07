package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.exception.ClientPhoneAlreadyExistsException;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import com.lian.marketing.transactionmicroservice.domain.utils.DomainUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

    private final IClientPersistencePort clientPersistencePort;

    @Override
    public Mono<Void> saveClient(Client client) {
        String phone = DomainUtils.transformPhoneNumber(client.getPhone());
        client.setPhone(phone);
        return clientPersistencePort.findClientByPhone(client.getPhone())
                .flatMap(existing -> {
                    log.warn("Client with phone {} already exists", client.getPhone());
                    return Mono.error(new ClientPhoneAlreadyExistsException("Phone number already exists"));
                })
                .switchIfEmpty(clientPersistencePort.saveClient(client))
                .then();
    }

    @Override
    public Mono<Boolean> existsByPhone(String phone) {
        return clientPersistencePort.findClientByPhone(phone)
                .map(client -> true)
                .defaultIfEmpty(false)
                .doOnNext(exists -> log.info("Client exists: {}", exists));
    }

    public Mono<UUID> findIdByPhone(String phone) {
        return clientPersistencePort.findIdByPhone(phone);
    }

    @Override
    public Mono<UUID> saveClientAndGetId(Client client) {
        return Mono.defer(() -> {
            client.setPhone(DomainUtils.transformPhoneNumber(client.getPhone()));
            return clientPersistencePort.saveClient(client);
        });
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return clientPersistencePort.userExists(id);
    }

    @Override
    public Mono<String> findClientNameById(UUID id) {
        return clientPersistencePort.findClientNameById(id);
    }

    @Override
    public Flux<Client> findAllByName(String name) {
        return clientPersistencePort.findAllByName(name);
    }

}

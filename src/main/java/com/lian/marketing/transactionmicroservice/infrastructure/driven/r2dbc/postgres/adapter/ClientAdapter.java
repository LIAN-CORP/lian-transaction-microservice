package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.IClientEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ClientAdapter implements IClientPersistencePort {

    private final ClientRepository clientRepository;
    private final IClientEntityMapper clientEntityMapper;

    @Override
    public Mono<UUID> saveClient(Client client) {
        log.info("Saving client {}", client);
        return clientRepository.save(clientEntityMapper.toEntity(client)).flatMap(clientEntity -> Mono.just(clientEntity.getId()));
    }

    @Override
    public Mono<Client> findClientByPhone(String phone) {
        return clientRepository.findByPhone(phone).map(clientEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> userExists(UUID id) {
        return clientRepository.existsById(id);
    }

    @Override
    public Mono<UUID> findIdByPhone(String phone) {
        return clientRepository.findIdByPhone(phone);
    }

    @Override
    public Mono<String> findClientNameById(UUID id) {
        return clientRepository.findClientNameById(id);
    }
}

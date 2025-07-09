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
    public Mono<Void> saveClient(Client client) {
        log.info("Saving client {}", client);
        return clientRepository.save(clientEntityMapper.toEntity(client)).then();
    }

    @Override
    public Mono<Client> findClientByPhone(String phone) {
        return clientRepository.findByPhone(phone).map(clientEntityMapper::toModel);
    }

    @Override
    public Mono<Boolean> userExists(UUID id) {
        return clientRepository.existsById(id);
    }
}

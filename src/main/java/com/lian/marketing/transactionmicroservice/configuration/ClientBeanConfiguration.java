package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.ClientUseCase;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter.ClientAdapter;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.IClientEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ClientBeanConfiguration {

    private final IClientEntityMapper clientEntityMapper;
    private final ClientRepository clientRepository;

    @Bean
    public IClientPersistencePort clientPersistencePort() {
        return new ClientAdapter(clientRepository, clientEntityMapper);
    }

    @Bean
    public IClientServicePort clientServicePort() {
        return new ClientUseCase(clientPersistencePort());
    }
}

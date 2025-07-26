package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.TransactionUseCase;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter.TransactionAdapter;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.ITransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TransactionBeanConfig {

    private final ITransactionEntityMapper transactionEntityMapper;
    private final TransactionRepository transactionRepository;
    private final WebClientConfig client;
    private final ClientBeanConfiguration clientBeanConfiguration;
    private final DetailTransactionBeanConfig detailTransactionBeanConfig;

    @Bean
    public ITransactionPersistencePort transactionPersistencePort() {
        return new TransactionAdapter(transactionRepository, transactionEntityMapper, client.userWebClient(), client.stockWebClient());
    }

    @Bean
    public ITransactionServicePort transactionServicePort() {
        return new TransactionUseCase(transactionPersistencePort(), clientBeanConfiguration.clientServicePort(), detailTransactionBeanConfig.detailTransactionServicePort());
    }
}

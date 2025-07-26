package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.IDetailTransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.DetailTransactionUseCase;
import com.lian.marketing.transactionmicroservice.domain.spi.IDetailTransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter.DetailTransactionAdapter;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.IDetailTransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.DetailTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DetailTransactionBeanConfig {
    private final IDetailTransactionEntityMapper detailTransactionEntityMapper;
    private final DetailTransactionRepository detailTransactionRepository;
    private final WebClientConfig client;

    @Bean
    public IDetailTransactionPersistencePort detailTransactionPersistencePort() {
        return new DetailTransactionAdapter(detailTransactionRepository, detailTransactionEntityMapper, client.stockWebClient());
    }

    @Bean
    public IDetailTransactionServicePort detailTransactionServicePort() {
        return new DetailTransactionUseCase(detailTransactionPersistencePort());
    }
}

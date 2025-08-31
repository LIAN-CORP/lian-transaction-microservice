package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.WorkbookUseCase;
import com.lian.marketing.transactionmicroservice.domain.spi.IWorkbookPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter.WorkbookAdapter;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.r2dbc.core.DatabaseClient;

@Configuration
@RequiredArgsConstructor
public class WorkbookBeanConfiguration {

    private final ConnectionFactory connectionFactory;

    @Bean
    public DatabaseClient databaseClient() {
        return DatabaseClient.create(connectionFactory);
    }

    @Bean
    public IWorkbookPersistencePort workbookPersistencePort() {
        return new WorkbookAdapter(databaseClient());
    }

    @Bean
    public IWorkbookServicePort workbookServicePort() {
        return new WorkbookUseCase(workbookPersistencePort());
    }

}

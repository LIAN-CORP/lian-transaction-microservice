package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.WorkbookUseCase;
import com.lian.marketing.transactionmicroservice.domain.spi.IWorkbookPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter.WorkbookAdapter;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WorkbookBeanConfiguration {

    private final WorkbookRepository repository;

    @Bean
    public IWorkbookPersistencePort workbookPersistencePort() {
        return new WorkbookAdapter(repository);
    }

    @Bean
    public IWorkbookServicePort workbookServicePort() {
        return new WorkbookUseCase(workbookPersistencePort());
    }

}

package com.lian.marketing.transactionmicroservice.configuration;

import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.usecase.WorkbookUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class WorkbookBeanConfiguration {

    private final TransactionBeanConfig transactionBeanConfig;
    private final ClientBeanConfiguration clientBeanConfiguration;
    private final DetailTransactionBeanConfig detailTransactionBeanConfig;

    @Bean
    public IWorkbookServicePort workbookServicePort() {
        return new WorkbookUseCase(
                transactionBeanConfig.transactionServicePort(),
                detailTransactionBeanConfig.detailTransactionServicePort(),
                clientBeanConfiguration.clientServicePort()
                );
    }

}

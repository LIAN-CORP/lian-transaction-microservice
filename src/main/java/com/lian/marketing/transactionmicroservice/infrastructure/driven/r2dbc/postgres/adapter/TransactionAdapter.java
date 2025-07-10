package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.model.ExistsResponse;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.ITransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class TransactionAdapter implements ITransactionPersistencePort {

    private final TransactionRepository transactionRepository;
    private final ITransactionEntityMapper transactionEntityMapper;

    @Qualifier("userWebClient")
    private final WebClient userWebClient;

    public TransactionAdapter(TransactionRepository transactionRepository,
                              ITransactionEntityMapper transactionEntityMapper,
                              @Qualifier("userWebClient") WebClient userWebClient) {
        this.transactionRepository = transactionRepository;
        this.transactionEntityMapper = transactionEntityMapper;
        this.userWebClient = userWebClient;
    }

    @Override
    public Mono<Void> saveTransaction(Transaction transaction) {
        return transactionRepository.save(transactionEntityMapper.toEntity(transaction))
                .doOnNext(t -> log.info(GeneralConstants.TRANSACTION_SAVED_SFL4J, t.getId()))
                .then();
    }

    @Override
    public Mono<Boolean> userExists(UUID id) {
        return userWebClient.get()
                .uri("/user/exists/{id}", id)
                .retrieve()
                .bodyToMono(ExistsResponse.class)
                .map(ExistsResponse::exists);
    }

}

package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.model.ExistsResponse;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.ITransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Slf4j
public class TransactionAdapter implements ITransactionPersistencePort {

    private final TransactionRepository transactionRepository;
    private final ITransactionEntityMapper transactionEntityMapper;

    @Qualifier("userWebClient")
    private final WebClient userWebClient;
    @Qualifier("productWebClient")
    private final WebClient productWebClient;

    public TransactionAdapter(TransactionRepository transactionRepository,
                              ITransactionEntityMapper transactionEntityMapper,
                              @Qualifier("userWebClient") WebClient userWebClient,
                              @Qualifier("productWebClient") WebClient productWebClient) {
        this.transactionRepository = transactionRepository;
        this.transactionEntityMapper = transactionEntityMapper;
        this.userWebClient = userWebClient;
        this.productWebClient = productWebClient;
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
                .uri("/user/exists/{id}", id.toString())
                .retrieve()
                .bodyToMono(ExistsResponse.class)
                .map(ExistsResponse::exists)
                .doOnNext(exists -> log.info("User exists: {}", exists));
    }

    @Override
    public Mono<Void> discountProductStock(List<ProductTransaction> productTransactions) {
        return productWebClient.post()
                .uri("/product/discount")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(productTransactions)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

}

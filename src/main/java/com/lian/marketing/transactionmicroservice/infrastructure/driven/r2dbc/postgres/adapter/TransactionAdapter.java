package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.ProductNotFoundException;
import com.lian.marketing.transactionmicroservice.domain.model.ExistsResponse;
import com.lian.marketing.transactionmicroservice.domain.model.PaymentTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TransactionEntity;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.ITransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
    @Qualifier("paymentWebClient")
    private final WebClient paymentWebClient;

    public TransactionAdapter(TransactionRepository transactionRepository,
                              ITransactionEntityMapper transactionEntityMapper,
                              @Qualifier("userWebClient") WebClient userWebClient,
                              @Qualifier("productWebClient") WebClient productWebClient,
                              @Qualifier("paymentWebClient") WebClient paymentWebClient) {
        this.transactionRepository = transactionRepository;
        this.transactionEntityMapper = transactionEntityMapper;
        this.userWebClient = userWebClient;
        this.productWebClient = productWebClient;
        this.paymentWebClient = paymentWebClient;
    }

    @Override
    public Mono<UUID> saveTransaction(Transaction transaction) {
        return transactionRepository.save(transactionEntityMapper.toEntity(transaction))
                .doOnNext(t -> log.info(GeneralConstants.TRANSACTION_SAVED_SFL4J, t.getId()))
                .map(TransactionEntity::getId);
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
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new ProductNotFoundException(GeneralConstants.PRODUCT_NOT_FOUND)))
                .toBodilessEntity()
                .then();
    }

    @Override
    public Mono<Void> addProductStock(List<ProductTransaction> productTransactions) {
        return productWebClient.post()
                .uri("/product/stock/add")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(productTransactions)
                .retrieve()
                .onStatus(HttpStatus.NOT_FOUND::equals, response -> Mono.error(new ProductNotFoundException(GeneralConstants.PRODUCT_NOT_FOUND)))
                .toBodilessEntity()
                .then();
    }

    @Override
    public Mono<Void> sendPaymentToMicroservice(PaymentTransaction paymentTransaction) {
        return paymentWebClient.post()
                .uri("/payment/transaction")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(paymentTransaction)
                .retrieve()
                .toBodilessEntity()
                .then();
    }

}

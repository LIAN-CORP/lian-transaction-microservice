package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.domain.spi.IDetailTransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.mapper.IDetailTransactionEntityMapper;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.DetailTransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
public class DetailTransactionAdapter implements IDetailTransactionPersistencePort {
    private final DetailTransactionRepository detailTransactionRepository;
    private final IDetailTransactionEntityMapper detailTransactionEntityMapper;

    @Qualifier("productWebClient")
    private final WebClient productWebClient;

    public DetailTransactionAdapter(DetailTransactionRepository detailTransactionRepository,
                                    IDetailTransactionEntityMapper detailTransactionEntityMapper,
                                    @Qualifier("productWebClient") WebClient productWebClient) {
        this.detailTransactionRepository = detailTransactionRepository;
        this.detailTransactionEntityMapper = detailTransactionEntityMapper;
        this.productWebClient = productWebClient;
    }

    @Override
    public Mono<Void> saveDetailTransaction(DetailTransaction detailTransaction) {
        return detailTransactionRepository.
                save(detailTransactionEntityMapper.toEntityFromModel(detailTransaction))
                .doOnNext(d -> log.info("Detail transaction saved: {}", d.getId()))
                .then();
    }

    @Override
    public Mono<Double> getProductPriceById(UUID productId) {
        return productWebClient.get()
                .uri("/product/price/{id}", productId.toString())
                .retrieve()
                .bodyToMono(Double.class)
                .doOnNext(price -> log.info("Product price: {}", price));
    }

    @Override
    public Mono<Double> getProductBuyPriceById(UUID productId) {
        return productWebClient.get()
                .uri("/product/price/buy/{id}", productId.toString())
                .retrieve()
                .bodyToMono(Double.class)
                .doOnNext(price -> log.info("Product price: {}", price));
    }

    @Override
    public Mono<Double> findDetailTransactionsByTransactionId(UUID transactionId) {
        return detailTransactionRepository.findTotalUnitPriceByTransactionId(transactionId);
    }
}

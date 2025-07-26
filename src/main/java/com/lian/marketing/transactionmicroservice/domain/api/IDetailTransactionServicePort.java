package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import reactor.core.publisher.Mono;

import java.util.List;

public interface IDetailTransactionServicePort {
    Mono<Void> createDetailTransaction(DetailTransaction detailTransaction, List<ProductTransaction> products);
}

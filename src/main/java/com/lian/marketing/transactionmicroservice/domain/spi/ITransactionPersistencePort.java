package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.CreditTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.PaymentTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface ITransactionPersistencePort {
    Mono<UUID> saveTransaction(Transaction transaction);
    Mono<Boolean> userExists(UUID id);
    Mono<Void> discountProductStock(List<ProductTransaction> productTransactions);
    Mono<Void> addProductStock(List<ProductTransaction> productTransactions);
    Mono<Void> sendPaymentToMicroservice(PaymentTransaction paymentTransaction);
    Mono<Void> sendCreditToMicroservice(CreditTransaction creditTransaction);
}

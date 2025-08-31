package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ITransactionPersistencePort {
    Mono<UUID> saveTransaction(Transaction transaction);
    Mono<Boolean> userExists(UUID id);
    Mono<Void> discountProductStock(List<ProductTransaction> productTransactions);
    Mono<Void> addProductStock(List<ProductTransaction> productTransactions);
    Mono<Void> sendPaymentToMicroservice(PaymentTransaction paymentTransaction);
    Mono<Void> sendCreditToMicroservice(CreditTransaction creditTransaction);
    Flux<Transaction> findAllTransactionsByDateRange(LocalDate start, LocalDate end);
    Flux<DebtTransactionExcel> findAllDebtsByDateRange(LocalDate start, LocalDate end);
}

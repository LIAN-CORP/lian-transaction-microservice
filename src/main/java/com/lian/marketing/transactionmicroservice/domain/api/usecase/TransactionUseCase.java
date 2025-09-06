package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.IDetailTransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.UserDoNotExistsException;
import com.lian.marketing.transactionmicroservice.domain.model.*;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.domain.utils.DomainUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class TransactionUseCase implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistencePort;
    private final IClientServicePort clientServicePort;
    private final IDetailTransactionServicePort detailTransactionServicePort;


    @Override
    public Mono<UUID> createTransaction(Transaction transaction) {
        String phone = DomainUtils.transformPhoneNumber(transaction.getClient().getPhone());
        transaction.getClient().setPhone(phone);
        return transactionPersistencePort.userExists(transaction.getUserId())
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new UserDoNotExistsException(GeneralConstants.USER_DO_NOT_EXISTS)))
                .then(clientServicePort.existsByPhone(transaction.getClient().getPhone()))
                .flatMap(client -> {
                    Mono<UUID> saveClientIfNeeded = client ? clientServicePort.findIdByPhone(transaction.getClient().getPhone()) : clientServicePort.saveClientAndGetId(transaction.getClient());

                    return saveClientIfNeeded.flatMap(clientId -> {
                       transaction.getClient().setId(clientId);
                       transaction.setTransactionDate(LocalDate.now());
                       log.info(GeneralConstants.SAVING_TRANSACTION_SFL4J, transaction);
                       return transactionPersistencePort.saveTransaction(transaction);
                    });
                });
    }

    @Override
    public Mono<Void> createCompleteTransaction(CompleteTransaction completeTransaction) {
        String type = completeTransaction.getTransaction().getTypeMovement().name();
        return switch (type) {
            case GeneralConstants.SELL_TRANSACTION -> processSellTransaction(completeTransaction);
            case GeneralConstants.BUY_TRANSACTION -> processBuyTransaction(completeTransaction);
            case GeneralConstants.CREDIT_TRANSACTION -> processCreditTransaction(completeTransaction);
            default -> Mono.empty();
        };
    }

    @Override
    public Flux<Transaction> findAllTransactionsByDateRange(LocalDate start, LocalDate end) {
        return transactionPersistencePort.findAllTransactionsByDateRange(start, end);
    }

    @Override
    public Flux<DebtTransactionExcel> findAllDebtsByDateRange(LocalDate start, LocalDate end) {
        return null;
    }

    private Mono<Void> processSellTransaction(CompleteTransaction completeTransaction) {
        return discountProductStock(completeTransaction.getProducts())
                .then(this.createTransaction(completeTransaction.getTransaction()))
                .flatMap(transactionId ->
                    createDetailAndPayment(completeTransaction, transactionId)
                );
    }

    private Mono<Void> processBuyTransaction(CompleteTransaction completeTransaction) {
        return addProductStock(completeTransaction.getProducts())
                .then(this.createTransaction(completeTransaction.getTransaction()))
                .flatMap(transactionId -> detailTransactionServicePort.createDetailTransaction(
                                new DetailTransaction(null, null, null, transactionId, null),
                                completeTransaction.getProducts(),
                                completeTransaction.getTransaction().getTypeMovement().name()
                        )
                );
    }

    private Mono<Void> processCreditTransaction(CompleteTransaction completeTransaction) {
        return discountProductStock(completeTransaction.getProducts())
                .then(this.createTransaction(completeTransaction.getTransaction()))
                .flatMap(transactionId ->
                        createDetailAndDebt(completeTransaction, transactionId)
                );
    }

    private Mono<Void> createDetailAndDebt(CompleteTransaction completeTransaction, UUID transactionId){
        return detailTransactionServicePort.createDetailTransaction(
            new DetailTransaction(null,
                null,
                null,
                transactionId,
                null),
                completeTransaction.getProducts(),
                completeTransaction.getTransaction().getTypeMovement().name()
            )
            .then(detailTransactionServicePort.getTotalAmountByTransactionId(transactionId))
            .flatMap(amount -> transactionPersistencePort.sendCreditToMicroservice(
                        new CreditTransaction(
                                BigDecimal.valueOf(amount),
                                completeTransaction.getTransaction().getClient().getId()
                        )
                ));
    }

    private Mono<Void> createDetailAndPayment(CompleteTransaction completeTransaction, UUID transactionId) {
        return detailTransactionServicePort.createDetailTransaction(
                new DetailTransaction(null,
                        null,
                        null,
                        transactionId,
                        null),
                completeTransaction.getProducts(),
                completeTransaction.getTransaction().getTypeMovement().name()
        )
        .then(detailTransactionServicePort.getTotalAmountByTransactionId(transactionId))
        .flatMap(amount -> {
            PaymentTransaction payment = new PaymentTransaction();
            payment.setAmount(amount.intValue());
            payment.setTransactionId(transactionId);
            payment.setPaymentMethod("CASH");
            payment.setClientId(completeTransaction.getTransaction().getClient().getId());
            payment.setDebtId(null);
            return transactionPersistencePort.sendPaymentToMicroservice(payment);
        });
    }

    private Mono<Void> discountProductStock(List<ProductTransaction> productTransactions) {
        return transactionPersistencePort.discountProductStock(productTransactions);
    }

    private Mono<Void> addProductStock(List<ProductTransaction> productTransactions) {
        return transactionPersistencePort.addProductStock(productTransactions);
    }
}

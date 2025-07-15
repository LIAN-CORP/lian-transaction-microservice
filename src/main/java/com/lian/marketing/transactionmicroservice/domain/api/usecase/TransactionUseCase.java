package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.UserDoNotExistsException;
import com.lian.marketing.transactionmicroservice.domain.model.CompleteTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.ProductTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import com.lian.marketing.transactionmicroservice.domain.utils.DomainUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class TransactionUseCase implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistencePort;
    private final IClientServicePort clientServicePort;


    @Override
    public Mono<Void> createTransaction(Transaction transaction) {
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
        return this.createTransaction(completeTransaction.getTransaction())
                .then(discountProductStock(completeTransaction.getProductTransactions()));
    }

    private Mono<Void> discountProductStock(List<ProductTransaction> productTransactions) {
        return transactionPersistencePort.discountProductStock(productTransactions);
    }
}

package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.exception.UserDoNotExistsException;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.spi.ITransactionPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class TransactionUseCase implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistencePort;
    private final IClientServicePort clientServicePort;


    @Override
    public Mono<Void> createTransaction(Transaction transaction) {
        return transactionPersistencePort.userExists(transaction.getUserId())
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new UserDoNotExistsException(GeneralConstants.USER_DO_NOT_EXISTS)))
                .then(clientServicePort.existsByPhone(transaction.getClient().getPhone()))
                .flatMap(client -> {
                    Mono<Void> saveClientIfNeeded = client ? Mono.empty() : clientServicePort.saveClient(transaction.getClient());
                    log.info(GeneralConstants.SAVING_TRANSACTION_SFL4J, transaction);
                    return saveClientIfNeeded.then(transactionPersistencePort.saveTransaction(transaction));
                });
    }
}

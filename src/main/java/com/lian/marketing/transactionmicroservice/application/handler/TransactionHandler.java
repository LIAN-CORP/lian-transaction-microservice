package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CompleteCreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.ICompleteTransactionMapper;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.model.ContentPage;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionHandler {
    private final ICompleteTransactionMapper transactionMapper;
    private final ITransactionServicePort transactionServicePort;

    public Mono<Void> saveTransaction(CompleteCreateTransactionRequest request, String userId) {
        return transactionServicePort.createCompleteTransaction(transactionMapper.toModelFromRequest(request), userId);
    }

    public Mono<ContentPage<Transaction>> findAllTransactions(int page, int size, String start, String end, UUID clientId, String type) {
        return transactionServicePort.findAllTransactionsByDate(page, size, start, end, clientId, type);
    }

    public Mono<Void> deleteTransactionById(UUID id){
        return transactionServicePort.deleteTransactionById(id);
    }
}

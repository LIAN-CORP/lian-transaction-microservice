package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CompleteCreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.ICompleteTransactionMapper;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionHandler {
    private final ICompleteTransactionMapper transactionMapper;
    private final ITransactionServicePort transactionServicePort;

    public Mono<Void> saveTransaction(CompleteCreateTransactionRequest request) {
        return transactionServicePort.createCompleteTransaction(transactionMapper.toModelFromRequest(request));
    }
}

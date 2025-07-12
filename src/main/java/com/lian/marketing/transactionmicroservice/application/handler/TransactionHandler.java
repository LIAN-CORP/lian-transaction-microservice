package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.ITransactionMapper;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TransactionHandler {
    private final ITransactionMapper transactionMapper;
    private final ITransactionServicePort transactionServicePort;

    public Mono<Void> saveTransaction(CreateTransactionRequest request) {
        return transactionServicePort.createTransaction(transactionMapper.toModelFromRequest(request));
    }
}

package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.handler.TransactionHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionHandler transactionHandler;

    @PostMapping
    public Mono<ResponseEntity<Void>> saveTransaction(
            @Valid @RequestBody CreateTransactionRequest request
            ) {
        return transactionHandler.saveTransaction(request).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
    }

}

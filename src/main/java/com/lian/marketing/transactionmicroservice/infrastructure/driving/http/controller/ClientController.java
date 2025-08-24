package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.handler.ClientHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {

    private final ClientHandler clientHandler;

    @PostMapping
    public Mono<ResponseEntity<Void>> saveClient(@Valid @RequestBody CreateClientRequest request) {
        return clientHandler.saveClient(request).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
    }

    @GetMapping("/exists/{id}")
    public Mono<ResponseEntity<Boolean>> userExistsById(@PathVariable("id") UUID id) {
        return clientHandler.userExistsById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}

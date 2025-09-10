package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.dto.request.UpdateClientRequest;
import com.lian.marketing.transactionmicroservice.application.handler.ClientHandler;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
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

    @GetMapping("/name/{id}")
    public Mono<ResponseEntity<String>> getClientNameById(@PathVariable("id") UUID id) {
        return clientHandler.findClientNameById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public Mono<ResponseEntity<List<Client>>> getClientsByName(@RequestParam(value = "name", required = false) String name) {
        return clientHandler.findAllByName(name)
          .collectList()
          .map(ResponseEntity::ok)
          .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping
    public Mono<ResponseEntity<Void>> updateClient(@Valid @RequestBody UpdateClientRequest client) {
        return clientHandler.updateClient(client).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
    }

}

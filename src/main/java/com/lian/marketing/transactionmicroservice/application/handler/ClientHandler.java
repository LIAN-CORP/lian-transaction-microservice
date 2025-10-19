package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.dto.request.UpdateClientRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.IClientMapper;
import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ClientHandler {
    private final IClientServicePort clientServicePort;
    private final IClientMapper clientMapper;

    public Mono<Void> saveClient(CreateClientRequest create) {
        return clientServicePort.saveClient(clientMapper.toModel(create));
    }

    public Mono<Boolean> userExistsById(UUID id){
        return clientServicePort.existsById(id);
    }

    public Mono<String> findClientNameById(UUID id){
        return clientServicePort.findClientNameById(id);
    }

    public Flux<Client> findAllByName(String name){
        return clientServicePort.findAllByName(name);
    }

    public Mono<Void> updateClient(UpdateClientRequest client){
        return clientServicePort.updateClient(clientMapper.toModelFromUpdate(client));
    }
}

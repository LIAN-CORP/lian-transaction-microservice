package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.IClientMapper;
import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
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
}

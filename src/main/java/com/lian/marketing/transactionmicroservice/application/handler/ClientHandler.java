package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.application.dto.request.CreateClientRequest;
import com.lian.marketing.transactionmicroservice.application.mapper.IClientMapper;
import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
}

package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

    private final IClientPersistencePort clientPersistencePort;

    @Override
    public void saveClient(Client client) {
        if(client.getPhone().length() < 10) {
            client.setPhone(GeneralConstants.COLOMBIA_PREFIX + client.getPhone());
        }
        client.setId(UUID.randomUUID());
        clientPersistencePort.saveClient(client);
    }
}

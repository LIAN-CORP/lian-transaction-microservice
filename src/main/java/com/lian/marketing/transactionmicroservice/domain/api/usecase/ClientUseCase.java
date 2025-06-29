package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.spi.IClientPersistencePort;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@RequiredArgsConstructor
public class ClientUseCase implements IClientServicePort {

    private static final Logger logger = LoggerFactory.getLogger(ClientUseCase.class);

    private final IClientPersistencePort clientPersistencePort;

    @Override
    public void saveClient(Client client) {
        if(client.getPhone().length() < 10) {
            client.setPhone(GeneralConstants.COLOMBIA_PREFIX + client.getPhone());
            logger.info("Phone number has no prefix, adding prefix to +57");
        }
        client.setId(UUID.randomUUID());
        clientPersistencePort.saveClient(client);
    }
}

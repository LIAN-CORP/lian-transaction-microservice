package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.Client;

public interface IClientPersistencePort {
    void saveClient(Client client);
}

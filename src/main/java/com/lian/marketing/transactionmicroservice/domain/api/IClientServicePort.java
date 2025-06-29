package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.Client;

public interface IClientServicePort {
    void saveClient(Client client);
}

package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Transaction {

    private UUID id;
    private type_movement typeMovement;
    private String transactionDate;
    private Client client;
    private UUID userId;

}

package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Transaction {

    private UUID id;
    private type_movement typeMovement;
    private String transactionDate;
    private Client client;
    private UUID userId;

}

package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class DetailTransaction {
    private UUID id;
    private Double unitPrice;
    private Integer quantity;
    private UUID transactionId;
    private UUID productId;
}

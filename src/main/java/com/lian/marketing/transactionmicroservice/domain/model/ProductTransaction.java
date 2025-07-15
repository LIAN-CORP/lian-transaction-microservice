package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class ProductTransaction {
    private UUID id;
    private Integer quantity;
    private Double priceSell;
}

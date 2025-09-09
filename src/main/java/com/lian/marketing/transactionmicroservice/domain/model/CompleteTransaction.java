package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class CompleteTransaction {
    private Transaction transaction;
    private String paymentMethod;
    private List<ProductTransaction> products;
}

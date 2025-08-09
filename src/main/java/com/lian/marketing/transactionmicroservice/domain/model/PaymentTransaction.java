package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class PaymentTransaction {
    private Integer amount;
    private String paymentMethod;
    private UUID clientId;
    private UUID transactionId;
    private UUID debtId;
}

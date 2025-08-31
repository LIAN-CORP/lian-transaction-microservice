package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class DebtTransactionExcel {
    private UUID id;
    private BigDecimal totalAmount;
    private BigDecimal remainingAmount;
    private String status;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private UUID clientId;
}

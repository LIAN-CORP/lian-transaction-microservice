package com.lian.marketing.transactionmicroservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class DebtReport {
    private String debtId;
    private String clientName;
    private String totalAmount;
    private String totalPaid;
    private String status;
    private String createdAt;
    private String updatedAt;
}

package com.lian.marketing.transactionmicroservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ReportFlatRow {
    private String transactionId;
    private String typeMovement;
    private String transactionDate;
    private String detailTransactionId;
    private String clientName;
    private String clientPhone;
    private String unitPrice;
    private String quantity;
    private String totalPrice;
    private String productName;
}

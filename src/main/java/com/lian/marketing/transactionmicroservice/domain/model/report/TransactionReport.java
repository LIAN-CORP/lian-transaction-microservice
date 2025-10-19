package com.lian.marketing.transactionmicroservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class TransactionReport {
    private String transactionId;
    private String typeMovement;
    private String transactionDate;
    private List<DetailTransactionReport> detailTransactionReports;
}

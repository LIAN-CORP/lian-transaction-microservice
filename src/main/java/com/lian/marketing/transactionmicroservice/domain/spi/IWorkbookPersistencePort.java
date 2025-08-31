package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.TransactionReport;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface IWorkbookPersistencePort {
    Flux<TransactionReport> findTransactionReportsByDate(LocalDate startDate, LocalDate endDate);
    Flux<DebtReport> findDebtReportsByDate(LocalDate startDate, LocalDate endDate);
}

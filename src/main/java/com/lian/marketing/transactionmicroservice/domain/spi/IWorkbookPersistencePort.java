package com.lian.marketing.transactionmicroservice.domain.spi;

import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TempTransactionReportEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface IWorkbookPersistencePort {
    Flux<TempTransactionReportEntity> findTransactionReportsByDate(LocalDate startDate, LocalDate endDate);
    Flux<DebtReport> findDebtReportsByDate(LocalDate startDate, LocalDate endDate);
    Mono<Void> callSpFillTempTransactionReport(LocalDate startDate, LocalDate endDate);
}

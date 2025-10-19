package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.domain.spi.IWorkbookPersistencePort;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TempTransactionReportEntity;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository.WorkbookRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RequiredArgsConstructor
public class WorkbookAdapter implements IWorkbookPersistencePort {

  private final WorkbookRepository repository;

  @Override
  public Flux<TempTransactionReportEntity> findTransactionReportsByDate(LocalDate startDate, LocalDate endDate) {
    return callSpFillTempTransactionReport(startDate, endDate).thenMany(repository.findTransactionReportsByDate());
  }

  @Override
  public Flux<DebtReport> findDebtReportsByDate(LocalDate startDate, LocalDate endDate) {
    return repository.findDebtReportsByDate(startDate, endDate);
  }

  @Override
  public Mono<Void> callSpFillTempTransactionReport(LocalDate startDate, LocalDate endDate) {
    return repository.callSpFillTempTransactionReport(startDate, endDate);
  }
}

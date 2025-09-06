package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.report.ExcelReport;
import reactor.core.publisher.Mono;

import java.time.LocalDate;


public interface IWorkbookServicePort {
    Mono<ExcelReport> generateWorkbook(LocalDate start, LocalDate end);
}

package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.report.ExcelReport;

import java.time.LocalDate;


public interface IWorkbookServicePort {
    ExcelReport generateWorkbook(LocalDate start, LocalDate end);
}

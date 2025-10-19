package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.model.report.ExcelReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReportHandler {
    private final IWorkbookServicePort workbookServicePort;

    public Mono<ExcelReport> generateReport(LocalDate start, LocalDate end) {
        return workbookServicePort.generateWorkbook(start, end);
    }
}

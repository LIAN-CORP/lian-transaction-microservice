package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.DebtReportEnum;
import com.lian.marketing.transactionmicroservice.domain.constants.DetailTransactionReportEnum;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.constants.TransactionReportEnum;
import com.lian.marketing.transactionmicroservice.domain.exception.ErrorCreatingExcelReportException;
import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.DetailTransactionReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.ExcelReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.TransactionReport;
import com.lian.marketing.transactionmicroservice.domain.spi.IWorkbookPersistencePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class WorkbookUseCase implements IWorkbookServicePort {

    private final IWorkbookPersistencePort workbookPersistencePort;

    @Override
    public Mono<ExcelReport> generateWorkbook(LocalDate start, LocalDate end) {
        return Mono.using(
          ByteArrayOutputStream::new,
          os -> {
              Workbook wb = new Workbook(os, "LIAN", "1.0");
              Worksheet transactionSheet = wb.newWorksheet("Transacciones");
              Worksheet detailTransactionSheet = wb.newWorksheet("Detalle de Transacciones");
              Worksheet debtSheet = wb.newWorksheet("Deudas");

              return generateTransactionSheet(transactionSheet, detailTransactionSheet, start, end)
                .then(generateDebtSheet(debtSheet, start, end))
                .then(Mono.fromCallable(() -> {
                    wb.close();
                    String fileName = start.toString().substring(0, 10).replace("-", "")
                      + "_" + end.toString().substring(0, 10).replace("-", "") + ".xlsx";
                    DataBuffer buffer = new DefaultDataBufferFactory().wrap(os.toByteArray());
                    return new ExcelReport(fileName, Flux.just(buffer));
                }));
          },
          os -> {
            try { os.close(); } catch (IOException e) {
              throw new ErrorCreatingExcelReportException(GeneralConstants.ERROR_CREATING_EXCEL_REPORT_SFL4J);
            }
          }
        );
    }

    private Mono<Void> generateTransactionSheet(Worksheet ws, Worksheet wsDetail, LocalDate start, LocalDate end) {
        // HEADERS
        for (int i = 0; i < TransactionReportEnum.values().length; i++) {
            ws.value(0, i, TransactionReportEnum.values()[i].getColumnName());
        }

        for (int i = 0; i < DetailTransactionReportEnum.values().length; i++) {
            wsDetail.value(0, i, DetailTransactionReportEnum.values()[i].getColumnName());
        }

        // DATA
        return workbookPersistencePort.findTransactionReportsByDate(start, end)
          .index()
          .flatMap(tuple -> {
              long i = tuple.getT1() + 1;
              TransactionReport t = tuple.getT2();

              ws.value((int) i, 0, t.getTransactionId());
              ws.value((int) i, 1, t.getTypeMovement());
              ws.value((int) i, 2, t.getTransactionDate());

              return Flux.fromIterable(t.getDetailTransactionReports())
                .index()
                .doOnNext(detailTuple -> {
                    long j = detailTuple.getT1() + 1;
                    DetailTransactionReport d = detailTuple.getT2();

                    wsDetail.value((int) j, 0, d.getDetailTransactionId());
                    wsDetail.value((int) j, 1, d.getClientName());
                    wsDetail.value((int) j, 2, d.getClientPhone());
                    wsDetail.value((int) j, 3, d.getProductName());
                    wsDetail.value((int) j, 4, d.getUnitPrice());
                    wsDetail.value((int) j, 5, d.getQuantity());
                    wsDetail.value((int) j, 6, d.getTotalPrice());
                    wsDetail.value((int) j, 7, t.getTransactionId());
                })
                .then();
          })
          .then();
    }


    private Mono<Void> generateDebtSheet(Worksheet ws, LocalDate start, LocalDate end) {
        // HEADERS
        for (int i = 0; i < DebtReportEnum.values().length; i++) {
            ws.value(0, i, DebtReportEnum.values()[i].getColumnName());
        }

        // DATA
        return workbookPersistencePort.findDebtReportsByDate(start, end)
          .index()
          .doOnNext(tuple -> {
              long row = tuple.getT1() + 1;
              DebtReport debt = tuple.getT2();

              ws.value((int) row, 0, debt.getDebtId());
              ws.value((int) row, 1, debt.getClientName());
              ws.value((int) row, 2, debt.getTotalAmount());
              ws.value((int) row, 3, debt.getTotalPaid());
              ws.value((int) row, 4, debt.getStatus());
              ws.value((int) row, 5, debt.getCreatedAt());
              ws.value((int) row, 6, debt.getUpdatedAt());
          })
          .then();
    }
}

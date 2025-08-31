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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
public class WorkbookUseCase implements IWorkbookServicePort {

    private final IWorkbookPersistencePort workbookPersistencePort;

    @Override
    public ExcelReport generateWorkbook(LocalDate start, LocalDate end) {
        try(
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Workbook wb = new Workbook(os, "LIAN", "1.0")
        ){
            Worksheet transactionSheet = wb.newWorksheet("Transacciones");
            Worksheet detailTransactionSheet = wb.newWorksheet("Detalle de Transacciones");
            Worksheet debtSheet = wb.newWorksheet("Deudas");

            generateTransactionSheet(transactionSheet, detailTransactionSheet, start, end);
            generateDebtSheet(debtSheet, start, end);

            DataBuffer buffer = new DefaultDataBufferFactory().wrap(os.toByteArray());
            String fileName = start.toString().substring(0, 10).replace("-", "")
                    + "_" + end.toString().substring(0, 10).replace("-", "") + ".xlsx";
            return new ExcelReport(fileName, Flux.just(buffer));
        } catch (IOException e) {
            log.error(GeneralConstants.ERROR_CREATING_EXCEL_REPORT_SFL4J, e.getMessage());
            return new ExcelReport(
                    "error.xlsx",
                    Flux.error(new ErrorCreatingExcelReportException(GeneralConstants.ERROR_CREATING_EXCEL_REPORT_SFL4J))
            );
        }
    }

    private void generateTransactionSheet(Worksheet ws, Worksheet wsDetail, LocalDate start, LocalDate end) {
        //HEADERS
        for(int i = 0; i < TransactionReportEnum.values().length; i++){
            ws.value(0,i, TransactionReportEnum.values()[i].getColumnName());
        }

        for(int i = 0; i < DetailTransactionReportEnum.values().length; i++){
            wsDetail.value(0,i, DetailTransactionReportEnum.values()[i].getColumnName());
        }

        //DATA
        workbookPersistencePort.findTransactionReportsByDate(start, end).collectList()
                .subscribe(transactions -> {
                    int i = 0;
                    int j = 0;
                    for(TransactionReport t : transactions){
                        ws.value(i, 0, t.getTransactionId());
                        ws.value(i, 1, t.getTypeMovement());
                        ws.value(i, 2, t.getTransactionDate());
                        for(DetailTransactionReport d : t.getDetailTransactionReports()){
                            wsDetail.value(j, 0, d.getDetailTransactionId());
                            wsDetail.value(j, 1, d.getClientName());
                            wsDetail.value(j, 2, d.getClientPhone());
                            wsDetail.value(j, 3, d.getProductName());
                            wsDetail.value(j, 4, d.getUnitPrice());
                            wsDetail.value(j, 5, d.getQuantity());
                            wsDetail.value(j, 6, d.getTotalPrice());
                            wsDetail.value(j, 7, t.getTransactionId());
                            j++;
                        }
                    }
                });
    }

    private void generateDebtSheet(Worksheet ws, LocalDate start, LocalDate end) {
        //HEADERS
        for(int i = 0; i < DebtReportEnum.values().length; i++){
            ws.value(0,i, DebtReportEnum.values()[i].getColumnName());
        }
        //DATA
        workbookPersistencePort.findDebtReportsByDate(start, end)
          .collectList()
          .subscribe(debts -> {
              int i = 0;
              for(DebtReport debt : debts){
                  ws.value(i, 0, debt.getDebtId());
                  ws.value(i, 1, debt.getClientName());
                  ws.value(i, 2, debt.getTotalAmount());
                  ws.value(i, 3, debt.getTotalPaid());
                  ws.value(i, 4, debt.getStatus());
                  ws.value(i, 5, debt.getCreatedAt());
                  ws.value(i, 6, debt.getUpdatedAt());
                  i++;
              }
          });
    }

}

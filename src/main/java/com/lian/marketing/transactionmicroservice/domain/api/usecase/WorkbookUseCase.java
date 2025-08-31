package com.lian.marketing.transactionmicroservice.domain.api.usecase;

import com.lian.marketing.transactionmicroservice.domain.api.IClientServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.IDetailTransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.ITransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.api.IWorkbookServicePort;
import com.lian.marketing.transactionmicroservice.domain.constants.DebtReportEnum;
import com.lian.marketing.transactionmicroservice.domain.constants.DetailTransactionReportEnum;
import com.lian.marketing.transactionmicroservice.domain.constants.GeneralConstants;
import com.lian.marketing.transactionmicroservice.domain.constants.TransactionReportEnum;
import com.lian.marketing.transactionmicroservice.domain.exception.ErrorCreatingExcelReportException;
import com.lian.marketing.transactionmicroservice.domain.model.DebtTransactionExcel;
import com.lian.marketing.transactionmicroservice.domain.model.DetailTransaction;
import com.lian.marketing.transactionmicroservice.domain.model.report.ExcelReport;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
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

    private final ITransactionServicePort transactionServicePort;
    private final IDetailTransactionServicePort detailTransactionServicePort;
    private final IClientServicePort clientServicePort;

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
        transactionServicePort.findAllTransactionsByDateRange(start, end).collectList()
                .subscribe(transactions -> {
                    int i = 0;
                    for(Transaction t : transactions){
                        ws.value(i, 0, t.getId().toString());
                        ws.value(i, 1, t.getTypeMovement().name());
                        ws.value(i, 2, t.getTransactionDate().toString());
                        ws.value(i, 3, t.getClient().getName());
                        i++;
                        //SEARCH FOR DETAIL TRANSACTIONS
                        detailTransactionServicePort.findAllDetailTransactionsByTransactionId(transactions.get(i).getId())
                                .collectList()
                                .subscribe(detail -> {
                                    int i2 = 0;
                                    for(DetailTransaction dt : detail){
                                        wsDetail.value(i2, 0, dt.getId().toString());
                                        wsDetail.value(i2, 1, dt.getUnitPrice().toString());
                                        wsDetail.value(i2, 2, dt.getQuantity().toString());
                                        wsDetail.value(i2, 4, String.valueOf(dt.getUnitPrice() * dt.getQuantity()));
                                        wsDetail.value(i2, 5, dt.getTransactionId().toString());
                                        wsDetail.value(i2, 6, dt.getProductId().toString());
                                        i2++;
                                    }
                                });
                    }
                });
    }

    private void generateDebtSheet(Worksheet ws, LocalDate start, LocalDate end) {
        //HEADERS
        for(int i = 0; i < DebtReportEnum.values().length; i++){
            ws.value(0,i, DebtReportEnum.values()[i].getColumnName());
        }
        //DATA
        transactionServicePort.findAllDebtsByDateRange(start, end).collectList()
                .subscribe(debts -> {
                    int i = 0;
                    for(DebtTransactionExcel debt : debts){
                        ws.value(i, 0, debt.getId().toString());
                        ws.value(i, 1, clientServicePort.findClientNameById(debt.getClientId()).toString());
                        ws.value(i, 2, debt.getTotalAmount().toString());
                        ws.value(i, 3, debt.getRemainingAmount().toString());
                        ws.value(i, 4, debt.getStatus());
                        ws.value(i, 5, debt.getCreatedAt().toString());
                        ws.value(i, 6, debt.getUpdatedAt().toString());
                        i++;
                    }
                });
    }

}

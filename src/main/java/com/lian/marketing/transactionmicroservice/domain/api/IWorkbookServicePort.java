package com.lian.marketing.transactionmicroservice.domain.api;

import com.lian.marketing.transactionmicroservice.domain.model.ExcelReport;
import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;

import java.time.LocalDate;


public interface IWorkbookServicePort {
    ExcelReport generateWorkbook(LocalDate start, LocalDate end);
}

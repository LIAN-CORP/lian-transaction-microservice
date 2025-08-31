package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.adapter;

import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.DetailTransactionReport;
import com.lian.marketing.transactionmicroservice.domain.model.report.ReportFlatRow;
import com.lian.marketing.transactionmicroservice.domain.model.report.TransactionReport;
import com.lian.marketing.transactionmicroservice.domain.spi.IWorkbookPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@RequiredArgsConstructor
public class WorkbookAdapter implements IWorkbookPersistencePort {

  private final DatabaseClient databaseClient;

  @Override
  public Flux<TransactionReport> findTransactionReportsByDate(LocalDate startDate, LocalDate endDate) {
    return databaseClient.sql("""
        SELECT * FROM SP_TO_EXCEL_REPORT(:startDate, :endDate)
        """)
      .bind("startDate", startDate.toString())
      .bind("endDate", endDate.toString())
      .map((row, meta) -> new ReportFlatRow(
          row.get("transaction_id", String.class),
          row.get("client_name", String.class),
          row.get("client_phone", String.class),
          row.get("type_movement", String.class),
          row.get("transaction_date", String.class),
          row.get("detail_transaction_id", String.class),
          row.get("unit_price", String.class),
          row.get("quantity", String.class),
          row.get("total_price", String.class),
          row.get("product_name", String.class)
        )
      )
      .all()
      .collectList()
      .flatMapMany(rows -> {
        Map<String, TransactionReport> transactionsGrouped = new LinkedHashMap<>();
        for (ReportFlatRow row : rows) {
          transactionsGrouped.computeIfAbsent(row.getTransactionId(), id -> {
            TransactionReport transactionReport = new TransactionReport();
            transactionReport.setTransactionId(id);
            transactionReport.setTypeMovement(row.getTypeMovement());
            transactionReport.setTransactionDate(row.getTransactionDate());
            transactionReport.setDetailTransactionReports(new ArrayList<>());
            return transactionReport;
          });
          DetailTransactionReport detailTransactionReport = new DetailTransactionReport();
          detailTransactionReport.setDetailTransactionId(row.getDetailTransactionId());
          detailTransactionReport.setProductName(row.getProductName());
          detailTransactionReport.setUnitPrice(row.getUnitPrice());
          detailTransactionReport.setQuantity(row.getQuantity());
          detailTransactionReport.setTotalPrice(row.getTotalPrice());
          transactionsGrouped.get(row.getTransactionId()).getDetailTransactionReports().add(detailTransactionReport);
        }
        return Flux.fromIterable(transactionsGrouped.values());
      });
  }

  @Override
  public Flux<DebtReport> findDebtReportsByDate(LocalDate startDate, LocalDate endDate) {
    return databaseClient.sql("""
        SELECT * FROM SP_EXCEL_REPORT_DEBTS(:startDate, :endDate)
        """)
      .bind("startDate", startDate.toString())
      .bind("endDate", endDate.toString())
      .map((row, meta) -> new DebtReport(
        row.get("id", String.class),
        row.get("client_name", String.class),
        row.get("total_amount", String.class),
        row.get("remaining_amount", String.class),
        row.get("status", String.class),
        row.get("created_at", String.class),
        row.get("updated_at", String.class)
      )).all();
  }
}

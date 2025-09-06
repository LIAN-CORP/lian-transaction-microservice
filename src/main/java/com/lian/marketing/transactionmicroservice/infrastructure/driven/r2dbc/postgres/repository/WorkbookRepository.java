package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.domain.model.report.DebtReport;
import com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity.TempTransactionReportEntity;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Repository
public class WorkbookRepository {

  private final DatabaseClient client;

  public WorkbookRepository(DatabaseClient databaseClient) {
    this.client = databaseClient;
  }

  public Mono<Void> callSpFillTempTransactionReport(LocalDate startDate, LocalDate endDate) {
    return client.sql("CALL sp_fill_temp_transaction_report(:start, :end)")
      .bind("start", startDate)
      .bind("end", endDate)
      .then();
  }

  public Flux<TempTransactionReportEntity> findTransactionReportsByDate() {
    String sql = "SELECT transaction_id, " +
      "client_name, " +
      "client_phone, " +
      "type_movement, " +
      "transaction_date, " +
      "detail_transaction_id, " +
      "unit_price, " +
      "quantity, " +
      "total_price, " +
      "product_id, " +
      "product_name " +
      "FROM temp_transaction_report";
    return client.sql(sql).mapProperties(TempTransactionReportEntity.class).all();
  }

  public Flux<DebtReport> findDebtReportsByDate(LocalDate startDate, LocalDate endDate) {
    return client.inConnectionMany(connection -> {

      Mono<Long> callSp = Mono.from(
        connection.createStatement("CALL sp_fill_temp_debt_report($1, $2)")
          .bind("$1", startDate)
          .bind("$2", endDate)
          .execute()
      ).flatMap(r -> Mono.from(r.getRowsUpdated()));

      Flux<DebtReport> selectAll = Flux.from(
        connection.createStatement("SELECT * FROM temp_debt_report").execute()
      )
      .flatMap(r -> r.map((row, metadata) -> {
        DebtReport report = new DebtReport();
        report.setDebtId(row.get("debt_id", String.class));
        report.setClientName(row.get("client_name", String.class));
        report.setTotalAmount(row.get("total_amount", String.class));
        report.setTotalPaid(row.get("remaining_amount", String.class));
        report.setStatus(row.get("status", String.class));
        report.setCreatedAt(row.get("created_at", String.class));
        report.setUpdatedAt(row.get("updated_at", String.class));
        return report;
      }));

      return callSp.thenMany(selectAll);

    });
  }

}

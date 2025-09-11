package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.repository;

import com.lian.marketing.transactionmicroservice.domain.model.Client;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import com.lian.marketing.transactionmicroservice.domain.model.type_movement;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class ManualRepository {
  private final DatabaseClient client;

  public ManualRepository(DatabaseClient databaseClient) {
    this.client = databaseClient;
  }

  public Flux<Transaction> findAllTransactions() {
    String sql = "SELECT t.*, c.name, c.phone FROM transactions t INNER JOIN client c ON t.client_id = c.id";
    return client.sql(sql).flatMap(r -> r.map((row, meta) -> {
      Transaction transaction = new Transaction();
      transaction.setId(row.get("id", UUID.class));
      transaction.setTransactionDate(row.get("transaction_date", LocalDate.class));
      transaction.setTypeMovement(type_movement.valueOf(row.get("type_movement", String.class)));
      transaction.setUserId(row.get("user_id", UUID.class));
      Client client = new Client();
      client.setName(row.get("name", String.class));
      client.setPhone(row.get("phone", String.class));
      transaction.setClient(client);
      return transaction;
    }));
  }

  public Flux<Transaction> findTransactionsByDate(LocalDate startDate, LocalDate endDate) {
    String sql = "SELECT t.*, c.name, c.phone FROM transactions t INNER JOIN client c ON t.client_id = c.id WHERE t.transaction_date BETWEEN :start AND :end";
    return client.sql(sql).bind("start", startDate).bind("end", endDate)
      .flatMap(r -> r.map((row, meta)-> {
        Transaction transaction = new Transaction();
        transaction.setId(row.get("id", UUID.class));
        transaction.setTransactionDate(row.get("transaction_date", LocalDate.class));
        transaction.setTypeMovement(type_movement.valueOf(row.get("type_movement", String.class)));
        transaction.setUserId(row.get("user_id", UUID.class));
        Client client = new Client();
        client.setName(row.get("name", String.class));
        client.setPhone(row.get("phone", String.class));
        transaction.setClient(client);
        return transaction;
      }));
  }
}

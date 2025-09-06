package com.lian.marketing.transactionmicroservice.infrastructure.driven.r2dbc.postgres.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@Table("temp_transaction_report")
public class TempTransactionReportEntity {
  private String transactionId;
  private String typeMovement;
  private String transactionDate;
  private String detailTransactionId;
  private String clientName;
  private String clientPhone;
  private String unitPrice;
  private String quantity;
  private String totalPrice;
  private String productName;
}

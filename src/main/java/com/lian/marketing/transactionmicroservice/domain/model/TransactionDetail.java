package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TransactionDetail {
  Transaction transaction;
  List<CompleteDetailTransaction> detail;
}

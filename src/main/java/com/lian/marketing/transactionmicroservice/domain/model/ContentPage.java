package com.lian.marketing.transactionmicroservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContentPage<T> {
  private int totalPage;
  private long totalElements;
  private int pageNumber;
  private int pageSize;
  private boolean isFirst;
  private boolean isLast;
  private List<T> content;

}
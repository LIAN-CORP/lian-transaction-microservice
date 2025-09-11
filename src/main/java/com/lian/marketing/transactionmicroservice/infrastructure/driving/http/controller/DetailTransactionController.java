package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.handler.DetailTransactionHandler;
import com.lian.marketing.transactionmicroservice.domain.model.CompleteDetailTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/detail-transaction")
@RequiredArgsConstructor
public class DetailTransactionController {

  private final DetailTransactionHandler detailTransactionHandler;

  @GetMapping
  public Mono<ResponseEntity<List<CompleteDetailTransaction>>> findDetailTransactionsByTransactionId(
    @RequestParam UUID transactionId
    ){
    return detailTransactionHandler.findDetailTransactionsByTransactionId(transactionId)
      .map(dt -> ResponseEntity.ok().body(dt))
      .defaultIfEmpty(ResponseEntity.noContent().build());
  }

}

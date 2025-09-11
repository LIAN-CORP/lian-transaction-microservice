package com.lian.marketing.transactionmicroservice.application.handler;

import com.lian.marketing.transactionmicroservice.domain.api.IDetailTransactionServicePort;
import com.lian.marketing.transactionmicroservice.domain.model.CompleteDetailTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DetailTransactionHandler {
  private final IDetailTransactionServicePort detailTransactionServicePort;

  public Mono<List<CompleteDetailTransaction>> findDetailTransactionsByTransactionId(UUID transactionId){
    return detailTransactionServicePort.findDetailTransactionsByTransactionId(transactionId);
  }
}

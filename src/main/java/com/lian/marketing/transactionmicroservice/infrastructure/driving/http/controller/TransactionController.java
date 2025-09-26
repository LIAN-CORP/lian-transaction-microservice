package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.dto.request.CompleteCreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.handler.ReportHandler;
import com.lian.marketing.transactionmicroservice.application.handler.TransactionHandler;
import com.lian.marketing.transactionmicroservice.domain.model.ContentPage;
import com.lian.marketing.transactionmicroservice.domain.model.Transaction;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionHandler transactionHandler;
    private final ReportHandler reportHandler;

    @PostMapping
    public Mono<ResponseEntity<Void>> saveTransaction(
            @Valid @RequestBody CompleteCreateTransactionRequest request
            ) {
        return transactionHandler.saveTransaction(request).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
    }

    @GetMapping(value = "/download/report", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public Mono<ResponseEntity<Flux<DataBuffer>>> downloadExcel(@RequestParam LocalDate start, @RequestParam LocalDate end) {
        return reportHandler.generateReport(start, end)
          .map(excel -> ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + excel.getFilename())
            .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
            .body(excel.getContent())
          );
    }

    @GetMapping
    public Mono<ResponseEntity<ContentPage<Transaction>>> findAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) UUID clientId
    ) {
        return transactionHandler.findAllTransactions(page, size, start, end, clientId, type)
                .map(transactionsPage -> ResponseEntity.ok().body(transactionsPage));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteTransactionById(@PathVariable("id") UUID id) {
        return transactionHandler.deleteTransactionById(id).then(Mono.defer(() -> Mono.just(ResponseEntity.ok().build())));
    }
}

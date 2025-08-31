package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.controller;

import com.lian.marketing.transactionmicroservice.application.dto.request.CompleteCreateTransactionRequest;
import com.lian.marketing.transactionmicroservice.application.handler.ReportHandler;
import com.lian.marketing.transactionmicroservice.application.handler.TransactionHandler;
import com.lian.marketing.transactionmicroservice.domain.model.ExcelReport;
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
        ExcelReport excel = reportHandler.generateReport(start, end);
        return Mono.just(
                ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" + excel.getFilename())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(excel.getContent())
        );
    }

}

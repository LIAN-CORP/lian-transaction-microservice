package com.lian.marketing.transactionmicroservice.domain.model.report;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class ExcelReport {
    private String filename;
    private Flux<DataBuffer> content;
}

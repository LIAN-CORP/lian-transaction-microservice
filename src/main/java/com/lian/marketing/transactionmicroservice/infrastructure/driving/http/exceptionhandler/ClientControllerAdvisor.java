package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.exceptionhandler;

import com.lian.marketing.transactionmicroservice.domain.exception.ClientPhoneAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class ClientControllerAdvisor {
    @ExceptionHandler(ClientPhoneAlreadyExistsException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleClientPhoneAlreadyExistsException(ClientPhoneAlreadyExistsException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                )));
    }
}

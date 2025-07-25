package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.exceptionhandler;

import com.lian.marketing.transactionmicroservice.domain.exception.ProductNotFoundException;
import com.lian.marketing.transactionmicroservice.domain.exception.TypeMovementNotValidException;
import com.lian.marketing.transactionmicroservice.domain.exception.UserDoNotExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class TransactionControllerAdvisor {

    @ExceptionHandler(UserDoNotExistsException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleUserDoNotExistsException(UserDoNotExistsException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                )));
    }

    @ExceptionHandler(TypeMovementNotValidException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleTypeMovementNotValidException(TypeMovementNotValidException e){
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                )));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleProductNotFoundException(ProductNotFoundException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now()
                )));
    }

}

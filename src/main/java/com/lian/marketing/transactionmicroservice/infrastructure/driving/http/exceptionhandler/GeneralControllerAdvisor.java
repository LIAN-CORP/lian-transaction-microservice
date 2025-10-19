package com.lian.marketing.transactionmicroservice.infrastructure.driving.http.exceptionhandler;

import com.lian.marketing.transactionmicroservice.domain.exception.ClientPhoneNumberIsNotValid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@ControllerAdvice
public class GeneralControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now(),
                        "METHOD_ARGUMENT_NOT_VALID"
                )));
    }

    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ExceptionResponse>> handlerServerWebInputException(ServerWebInputException e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now(),
                        "SERVER_WEB_INPUT_EXCEPTION"
                )));
    }

    @ExceptionHandler(ClientPhoneNumberIsNotValid.class)
    public Mono<ResponseEntity<ExceptionResponse>> handleClientPhoneNumberIsNotValid(ClientPhoneNumberIsNotValid e) {
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(
                        HttpStatus.BAD_REQUEST.toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        e.getMessage(),
                        LocalDateTime.now(),
                        "CLIENT_PHONE_IS_NOT_VALID"
                )));
    }

}

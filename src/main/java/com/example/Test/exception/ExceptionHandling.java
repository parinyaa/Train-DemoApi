package com.example.Test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(Exception e) {
        ExceptionResponse error = ExceptionResponse.builder()
                .errorCode("INTERNAL_SERVER_ERROR")
                .errorMsg(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundExceptions(NotFoundException e) {
        ExceptionResponse error = ExceptionResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMsg(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestExceptions(BadRequestException e) {
        ExceptionResponse error = ExceptionResponse.builder()
                .errorCode(e.getErrorCode())
                .errorMsg(e.getMessage())
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}

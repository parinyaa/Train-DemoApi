package com.example.Test.exception;

public class BadRequestException extends RuntimeException {

    private String errorCode = "ERR10001";

    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
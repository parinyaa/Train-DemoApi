package com.example.Test.exception;

public class NotFoundException extends RuntimeException {

    private String errorCode = "ERR10002";

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}

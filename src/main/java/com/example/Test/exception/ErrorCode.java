package com.example.Test.exception;

public enum ErrorCode {
    ERR_NOT_FOUND("ERR10010"),
    ERR_PUT("ERR10011"),
    ERR_PATCH("ERR10011");

    public final String code;

    private ErrorCode(String code) {
        this.code = code;
    }
}

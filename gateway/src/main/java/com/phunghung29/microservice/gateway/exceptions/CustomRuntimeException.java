package com.phunghung29.microservice.gateway.exceptions;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    private String statusCode;
    private String errorCode;
    private String errorType;

    public CustomRuntimeException() {
    }

    public CustomRuntimeException(String statusCode, String code, String errorType, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = code;
        this.errorType = errorType;
    }
}

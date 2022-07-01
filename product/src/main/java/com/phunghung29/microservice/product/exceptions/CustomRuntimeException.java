package com.phunghung29.microservice.product.exceptions;

import lombok.Getter;

@Getter
public class CustomRuntimeException extends RuntimeException {

    String statusCode;
    String errorCode;

    String errorType;

    public CustomRuntimeException(String code, String errorType, String message) {
        super(message);
        this.errorCode = code;
        this.errorType = errorType;
    }

    public CustomRuntimeException(String statusCode, String code, String errorType, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = code;
        this.errorType = errorType;
    }
}

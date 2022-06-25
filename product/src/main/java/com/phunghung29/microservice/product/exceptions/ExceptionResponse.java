package com.phunghung29.microservice.product.exceptions;

import lombok.Getter;

@Getter
public class ExceptionResponse extends RuntimeException {

    String statusCode;
    String errorCode;

    String errorType;

    public ExceptionResponse(String code, String errorType, String message) {
        super(message);
        this.errorCode = code;
        this.errorType = errorType;
    }

    public ExceptionResponse(String statusCode, String code, String errorType, String message) {
        super(message);
        this.statusCode = statusCode;
        this.errorCode = code;
        this.errorType = errorType;
    }
}

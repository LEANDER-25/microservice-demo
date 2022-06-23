package com.phunghung29.microservice.commons.exceptions;

import lombok.Getter;

@Getter
public class ExceptionResponse extends RuntimeException {
    String errorCode;

    String errorType;

    public ExceptionResponse(String code, String errorType, String message) {
        super(message);
        this.errorCode = code;
        this.errorType = errorType;
    }
}

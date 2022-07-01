package com.phunghung29.microservice.user.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomRuntimeException extends RuntimeException {

    String statusCode;
    String errorCode;

    String errorType;

    List<String> messages;

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

    public CustomRuntimeException(String statusCode, String code, String errorType, List<String> messages) {
        super("");
        this.statusCode = statusCode;
        this.errorCode = code;
        this.errorType = errorType;
        this.messages = messages;
    }
}

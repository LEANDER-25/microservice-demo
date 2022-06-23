package com.phunghung29.microservice.gateway.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends ExceptionResponse {
    public static final String CODE = "404";
    public static final String TYPE = "NOT FOUND";

    public NotFoundException(String message) {
        super(CODE, CODE, TYPE, message);
    }

    public NotFoundException(String code, String message) {
        super(CODE, CODE.concat("-").concat(code), TYPE, message);
    }
}

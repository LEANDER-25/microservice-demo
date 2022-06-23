package com.phunghung29.microservice.commons.exceptions;

import lombok.Getter;

@Getter
public class ExistedException extends ExceptionResponse {
    public static final String CODE = "409";

    public ExistedException(String message) {
        super(CODE, "EXISTED", message);
    }

    public ExistedException(String code, String message) {
        super(CODE.concat("-").concat(code), "EXISTED", message);
    }
}

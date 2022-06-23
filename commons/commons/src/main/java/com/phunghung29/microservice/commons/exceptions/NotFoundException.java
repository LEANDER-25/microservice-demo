package com.phunghung29.microservice.commons.exceptions;

import lombok.Getter;

@Getter
public class NotFoundException extends ExceptionResponse {
    public static final String CODE = "404";

    public NotFoundException(String message) {
        super(CODE, "NOT FOUND", message);
    }

    public NotFoundException(String code, String message) {
        super(CODE.concat("-").concat(code), "NOT FOUND", message);
    }
}

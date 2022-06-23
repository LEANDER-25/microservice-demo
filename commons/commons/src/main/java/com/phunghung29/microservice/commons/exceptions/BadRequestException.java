package com.phunghung29.microservice.commons.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends ExceptionResponse {
    public static final String CODE = "400";

    public BadRequestException(String message) {
        super(CODE, "BAD REQUEST", message);
    }

    public BadRequestException(String code, String message) {
        super(CODE.concat("-").concat(code), "BAD REQUEST", message);
    }
}

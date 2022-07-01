package com.phunghung29.microservice.user.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends CustomRuntimeException {
    public static final String CODE = "400";
    public static final String TYPE = "BAD REQUEST";

    public BadRequestException(String message) {
        super(CODE, CODE, TYPE, message);
    }

    public BadRequestException(String code, String message) {
        super(CODE, CODE.concat("-").concat(code), TYPE, message);
    }
}

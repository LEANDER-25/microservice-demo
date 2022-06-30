package com.phunghung29.microservice.gateway.exceptions;

import lombok.Getter;

@Getter
public class ExistedException extends CustomRuntimeException {
    public static final String CODE = "409";
    public static final String TYPE = "EXISTED";

    public ExistedException(String message) {
        super(CODE, CODE, TYPE, message);
    }

    public ExistedException(String code, String message) {
        super(CODE, CODE.concat("-").concat(code), TYPE, message);
    }
}

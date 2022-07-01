package com.phunghung29.microservice.product.exceptions;

import lombok.Getter;

@Getter
public class UnAuthorizationException extends CustomRuntimeException {
    public static final String CODE = "401";
    public static final String TYPE = "UN-AUTHORIZATION";

    public UnAuthorizationException(String message) {
        super(CODE, CODE, TYPE, message);
    }

    public UnAuthorizationException(String code, String message) {
        super(CODE, CODE.concat("-").concat(code), TYPE, message);
    }
}

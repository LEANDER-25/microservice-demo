package com.phunghung29.microservice.gateway.exceptions;

import lombok.Getter;

@Getter
public class InternalServerException extends ExceptionResponse{
    public static final String CODE = "500";
    public static final String TYPE = "INTERNAL SERVER ERROR";

    public InternalServerException(String message) {
        super(CODE, CODE, TYPE, message);
    }

    public InternalServerException(String code, String message) {
        super(CODE, CODE.concat("-").concat(code), TYPE, message);
    }
}

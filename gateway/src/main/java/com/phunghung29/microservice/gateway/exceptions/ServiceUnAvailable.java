package com.phunghung29.microservice.gateway.exceptions;

import lombok.Getter;

@Getter
public class ServiceUnAvailable extends CustomRuntimeException {
    public static final String CODE = "503";
    public static final String TYPE = "SERVICE UNAVAILABLE";

    public ServiceUnAvailable(String message) {
        super(CODE, CODE.concat("-SERVICE-UNAVAILABLE"), TYPE, message);
    }
}

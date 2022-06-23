package com.phunghung29.microservice.gateway.controllers;

import com.phunghung29.microservice.gateway.exceptions.*;
import com.phunghung29.microservice.gateway.response.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<?> handleException(ExceptionResponse exception, WebRequest webRequest) {
        return ResponseTemplate.error(Integer.parseInt(exception.getStatusCode()), exception).release();
    }
}

package com.phunghung29.microservice.user.controllers;

import com.phunghung29.microservice.user.exceptions.BadRequestException;
import com.phunghung29.microservice.user.exceptions.ExceptionCode;
import com.phunghung29.microservice.user.exceptions.CustomRuntimeException;
import com.phunghung29.microservice.user.response.ResponseTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(CustomRuntimeException.class)
    public ResponseEntity<?> handleException(CustomRuntimeException exception, WebRequest webRequest) {
        return ResponseTemplate.error(exception).setStatus(500).release();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        CustomRuntimeException exception = new CustomRuntimeException(
                String.valueOf(HttpStatus.BAD_REQUEST.value()),
                ExceptionCode.INVALID_FORMAT,
                BadRequestException.TYPE,
                new ArrayList<>()
        );
        bindingResult.getAllErrors().forEach(err -> exception.getMessages().add(err.getDefaultMessage()));
        return ResponseTemplate.error(400, exception).release();
    }
}

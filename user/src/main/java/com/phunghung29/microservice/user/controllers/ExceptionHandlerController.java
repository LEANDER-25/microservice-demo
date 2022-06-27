package com.phunghung29.microservice.user.controllers;

import com.phunghung29.microservice.user.exceptions.BadRequestException;
import com.phunghung29.microservice.user.exceptions.ExceptionCode;
import com.phunghung29.microservice.user.exceptions.ExceptionResponse;
import com.phunghung29.microservice.user.response.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.net.BindException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ExceptionResponse.class)
    public ResponseEntity<?> handleException(ExceptionResponse exception, WebRequest webRequest) {
        return ResponseTemplate.error(exception).release();
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> handleBindingException(BindException bindException, WebRequest webRequest) {
        ExceptionResponse exception = new ExceptionResponse(BadRequestException.CODE, ExceptionCode.INVALID_FORMAT, BadRequestException.TYPE, bindException.getMessage());
        return ResponseTemplate.error(Integer.parseInt(exception.getStatusCode()), exception).release();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest) {
        ExceptionResponse exception = new ExceptionResponse("400", ExceptionCode.INVALID_FORMAT, BadRequestException.TYPE, new ArrayList<>());
        BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
        bindingResult.getAllErrors().forEach(err -> exception.getMessages().add(err.getDefaultMessage()));
        return ResponseTemplate.error(400, exception).release();
    }
}

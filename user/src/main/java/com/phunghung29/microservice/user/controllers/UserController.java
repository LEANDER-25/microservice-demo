package com.phunghung29.microservice.user.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    @GetMapping("/**")
    public ResponseEntity<?> transitRequest(HttpServletRequest request) {
        log.info("In User Controller");
        return null;
    }
}

package com.phunghung29.microservice.gateway.controllers;

import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.dto.LoginRequestDTO;
import com.phunghung29.microservice.gateway.response.ResponseTemplate;
import com.phunghung29.microservice.gateway.services.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class GatewayController {

    private final GatewayService gatewayService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        LoginDTO loginDTO = gatewayService.login(loginRequestDTO, request.getHeader(HttpHeaders.USER_AGENT), request.getHeader("Client"));
        return ResponseTemplate.success(loginDTO).release();
    }

    @PostMapping("/generate")
    public void generate() {
        gatewayService.start();
    }
}

package com.phunghung29.microservice.gateway.controllers;

import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.dto.LoginRequestDTO;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.response.ResponseTemplate;
import com.phunghung29.microservice.gateway.services.GatewayService;
import com.phunghung29.microservice.gateway.services.TokenHandleService;
import com.phunghung29.microservice.gateway.utils.CustomHttpHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class GatewayController {

    private final GatewayService gatewayService;
    private final TokenHandleService tokenHandleService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpServletRequest request) {
        LoginDTO loginDTO = gatewayService.login(loginRequestDTO, request.getHeader(HttpHeaders.USER_AGENT), request.getHeader(CustomHttpHeader.CLIENTID));
        return ResponseTemplate.success(loginDTO).release();
    }

    @PostMapping("/token/verify")
    public ResponseEntity<?> verify(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String token,
            @RequestHeader(HttpHeaders.USER_AGENT) String userAgent,
            @RequestHeader(CustomHttpHeader.CLIENTID) String clientId) {
        return ResponseTemplate.success(tokenHandleService.verifyToken(token, clientId, userAgent)).release();
    }

    @PostMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String refreshToken) {
        return ResponseTemplate.success(tokenHandleService.refreshAccessToken(refreshToken)).release();
    }
}

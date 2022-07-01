package com.phunghung29.microservice.gateway.services;

import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.dto.LoginRequestDTO;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.entities.UserSession;

import java.util.Map;
import java.util.UUID;

public interface GatewayService {

    void start();

    String generateToken(Map<String, Object> payload, org.springframework.security.core.userdetails.User user, boolean isAccessToken);
    LoginDTO login(LoginRequestDTO loginRequestDTO, String userAgent, String encryptedClientID);
    UserSession checkSession(UUID userId, String userAgent, String encryptedClientID);
    void overrideSession(UUID userId, String token, String userAgent, String encryptedClientID);
    String createClientID(String rawKey);
    User findByEmail(String email);
}

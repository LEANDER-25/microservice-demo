package com.phunghung29.microservice.gateway.services;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.entities.UserSession;

public interface TokenHandleService {
    boolean verifyToken(String token, String clientId, String userAgent);
    UserSession checkToken(String token, boolean isParse);
    User handleToken(String token, boolean isParse, String clientId, String userAgent);
    LoginDTO refreshAccessToken(String refreshToken);
    DecodedJWT crackToken(String token);
}

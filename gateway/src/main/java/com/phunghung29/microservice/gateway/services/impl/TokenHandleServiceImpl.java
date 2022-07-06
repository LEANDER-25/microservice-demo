package com.phunghung29.microservice.gateway.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.phunghung29.microservice.gateway.dto.LoginDTO;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.entities.UserSession;
import com.phunghung29.microservice.gateway.exceptions.ExceptionCode;
import com.phunghung29.microservice.gateway.exceptions.UnAuthorizationException;
import com.phunghung29.microservice.gateway.repositories.UserRepository;
import com.phunghung29.microservice.gateway.repositories.UserSessionRepository;
import com.phunghung29.microservice.gateway.services.TokenHandleService;
import com.phunghung29.microservice.gateway.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenHandleServiceImpl implements TokenHandleService {
    private final UserSessionRepository userSessionRepository;
    private final UserRepository userRepository;

    @Override
    public boolean verifyToken(String token, String clientId, String userAgent) {
        User detectedUser = handleToken(token, false, clientId, userAgent);
        return detectedUser != null;
    }

    @Override
    public UserSession checkToken(String token, boolean isParse) {
        if (!token.startsWith("Bearer") && !isParse) {
            throw new UnAuthorizationException(ExceptionCode.TOKEN, "Missing Bearer prefix");
        }
        if (token.startsWith("Bearer") && !isParse) {
            token = token.split(" ")[1];
        }
        return userSessionRepository.findByToken(token);
    }

    @Override
    public User handleToken(String token, boolean isParse, String clientId, String userAgent) {
        Properties prop = Utils.loadProperties("jwt.setting.properties");
        DecodedJWT decodedJWT = crackToken(token);
        Date issuedAt = decodedJWT.getIssuedAt();
        Date expireAt = decodedJWT.getExpiresAt();
        long expireTime = expireAt.getTime() - issuedAt.getTime();
        long accessTime = Long.parseLong(prop.getProperty("access_expired"));
        long refreshTime = Long.parseLong(prop.getProperty("refresh_expired"));
        boolean isAccessToken = accessTime == expireTime;
        boolean isRefreshToken = refreshTime == expireTime;
        Map<String, Claim> tokenClaims = decodedJWT.getClaims();
        assert tokenClaims != null;
        String email = tokenClaims.get("sub").asString();
        User user = userRepository.findByEmail(email);
        if (isRefreshToken && checkToken(token, true) == null) {
            throw new UnAuthorizationException(ExceptionCode.TOKEN_NOT_FOUND, "Token not found");
        }
        if (isAccessToken) {
            if (clientId == null || clientId.isEmpty() || clientId.isBlank()) {
                throw new UnAuthorizationException(ExceptionCode.CLIENT_MISSING, "Missing Client ID Header");
            }
            if (userAgent == null || userAgent.isEmpty() || userAgent.isBlank()) {
                throw new UnAuthorizationException(ExceptionCode.USER_AGENT_MISSING, "Missing User Agent Header");
            }
            UserSession userSession = userSessionRepository.findByUserID(user.getId(), userAgent, clientId);
            if (userSession == null) {
                throw new UnAuthorizationException(ExceptionCode.TOKEN_EXPIRED, "Access token is expired");
            }
            String savedToken = userSession.getToken();
            if (savedToken == null) {
                throw new UnAuthorizationException(ExceptionCode.TOKEN_EXPIRED, "Access token is expired");
            }
        }
        if (!isAccessToken && !isRefreshToken) {
            throw new UnAuthorizationException(ExceptionCode.TOKEN_UNKNOWN, "Unknown token");
        }
        return user;
    }

    @Override
    public LoginDTO refreshAccessToken(String refreshToken) {
        return null;
    }

    @Override
    public DecodedJWT crackToken(String token) {
        Properties prop = Utils.loadProperties("jwt.setting.properties");
        String secret = prop.getProperty("key");
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }
}

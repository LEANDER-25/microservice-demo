package com.phunghung29.microservice.gateway.filters.jwt;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.exceptions.ExceptionCode;
import com.phunghung29.microservice.gateway.exceptions.UnAuthorizationException;
import com.phunghung29.microservice.gateway.response.ResponseTemplate;
import com.phunghung29.microservice.gateway.services.GatewayService;
import com.phunghung29.microservice.gateway.utils.CustomHttpHeader;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
public class JwtHandleFilter extends OncePerRequestFilter {

    private final GatewayService gatewayService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        assert token.startsWith("Bearer");
        token = token.split(" ")[1];
        try {
            User user = gatewayService.handleToken(
                    token,
                    true,
                    request.getHeader(CustomHttpHeader.CLIENTID),
                    request.getHeader(HttpHeaders.USER_AGENT)
            );
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(user.getRole().getRoleName()));
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    user, user, authorities
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            filterChain.doFilter(request, response);
        } catch (TokenExpiredException | JWTDecodeException | IOException | UnAuthorizationException e) {
            ResponseTemplate.error(new UnAuthorizationException(ExceptionCode.TOKEN, e.getMessage())).build().releaseAsServlet(response);
        }
    }
}

package com.phunghung29.microservice.gateway.controllers;


import com.phunghung29.microservice.gateway.configs.YamlProp;
import com.phunghung29.microservice.gateway.entities.User;
import com.phunghung29.microservice.gateway.services.RestInternalCommunicationService;
import com.phunghung29.microservice.gateway.utils.InternalServiceName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final RestInternalCommunicationService restInternalCommunicationService;

    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> transitRequest(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.info("In User Controller");
        log.info("User has email: {} and id: {}", user.getEmail(), user.getId());
        return restInternalCommunicationService.redirect(InternalServiceName.USER, request.getServletPath(), "/v1/users");
    }
}

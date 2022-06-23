package com.phunghung29.microservice.gateway.controllers;


import com.phunghung29.microservice.gateway.configs.YamlProp;
import com.phunghung29.microservice.gateway.utils.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Properties;

@RestController
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController {

//    private final MicroserviceSettingsProperties microserviceSettingsProperties;

    private final YamlProp yamlProp;

    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> transitRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("In User Controller");
        String servletPath = request.getServletPath();
        String endPointPath = servletPath.substring("/v1/users/".length());
        log.info("ServletPath: {}", servletPath);
        log.info("EndPointPath: {}", endPointPath);
        log.info("User Service Url: {}", yamlProp.getSettings().get("user-service").getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, yamlProp.getSettings().get("user-service").getUrl() + endPointPath);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }
}

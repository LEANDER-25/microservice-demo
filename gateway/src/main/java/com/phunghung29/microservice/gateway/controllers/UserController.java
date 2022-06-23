package com.phunghung29.microservice.gateway.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public class UserController {
    @RequestMapping(value = "/**", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseEntity<?> transitRequest(HttpServletRequest request, HttpServletResponse response) {
        log.info("In User Controller");
        String servletPath = request.getServletPath();
        String endPointPath = servletPath.substring("/v1/users/".length());
        log.info("ServletPath: {}", servletPath);
        log.info("EndPointPath: {}", endPointPath);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, "http://localhost:8081/api/v1/users/" + endPointPath);
        return new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);
    }
}

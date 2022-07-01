package com.phunghung29.microservice.gateway.services.impl;

import com.phunghung29.microservice.gateway.configs.SubServiceSetting;
import com.phunghung29.microservice.gateway.configs.YamlProp;
import com.phunghung29.microservice.gateway.exceptions.ServiceUnAvailable;
import com.phunghung29.microservice.gateway.services.RestInternalCommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestInternalCommunicationServiceImpl implements RestInternalCommunicationService {

    private final YamlProp yamlProp;

    @Override
    public ResponseEntity<?> redirect(String service, String servletPath, String controllerPath) {
        String endPointPath = servletPath.substring(controllerPath.length());
        log.info("ServletPath: {}", servletPath);
        log.info("EndPointPath: {}", endPointPath);
        SubServiceSetting serviceSetting = yamlProp.getSettings().get(service);
        log.info("Service Url: {}", serviceSetting.getUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.LOCATION, serviceSetting.getUrl() + endPointPath);
        ResponseEntity<?> responseFromUserService = new ResponseEntity<>(httpHeaders, HttpStatus.FOUND);

        if (responseFromUserService.getBody() == null) {
            throw new ServiceUnAvailable("Can not connect to service: " + service);
        }
        return responseFromUserService;
    }
}

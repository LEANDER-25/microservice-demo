package com.phunghung29.microservice.gateway.services;

import org.springframework.http.ResponseEntity;

public interface RestInternalCommunicationService {
    ResponseEntity<?> redirect(String service, String servletPath, String controllerPath);
}

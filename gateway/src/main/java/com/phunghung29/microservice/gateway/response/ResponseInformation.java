package com.phunghung29.microservice.gateway.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ResponseInformation {
    private String timestamp;
    private UUID uuid;
    private Integer statusCode;

    public ResponseInformation(int statusCode) {
        this.timestamp = Instant.now().toString();
        uuid = UUID.randomUUID();
        this.statusCode = statusCode;
    }

    public ResponseInformation() {
        this.timestamp = Instant.now().toString();
        uuid = UUID.randomUUID();
    }

    public String getTimestamp() {
        return timestamp == null ? Instant.now().toString() : timestamp;
    }

    public UUID getUuid() {
        return uuid == null ? UUID.randomUUID() : uuid;
    }
}

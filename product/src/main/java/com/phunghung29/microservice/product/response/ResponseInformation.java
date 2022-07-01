package com.phunghung29.microservice.product.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ResponseInformation {
    Instant timestamp;
    UUID uuid;
    Integer statusCode;

    public ResponseInformation(int statusCode) {
        this.timestamp = Instant.now();
        uuid = UUID.randomUUID();
        this.statusCode = statusCode;
    }

    public ResponseInformation() {
        this.timestamp = Instant.now();
        uuid = UUID.randomUUID();
    }

    public Instant getTimestamp() {
        return timestamp == null ? Instant.now() : timestamp;
    }

    public UUID getUuid() {
        return uuid == null ? UUID.randomUUID() : uuid;
    }
}

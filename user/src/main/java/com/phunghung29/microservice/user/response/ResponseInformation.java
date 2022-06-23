package com.phunghung29.microservice.user.response;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
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
    public Map<String, Object> getInformation() {
        Map<String, Object> header = new HashMap<>();
        header.put("timestamp", getTimestamp());
        header.put("uuid", getUuid());
        header.put("statusCode", getStatusCode());
        return header;
    }
}

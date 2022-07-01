package com.phunghung29.microservice.gateway.configs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SubServiceSetting {
    private String name;
    private String port;
    private String version;
    private String url;
}

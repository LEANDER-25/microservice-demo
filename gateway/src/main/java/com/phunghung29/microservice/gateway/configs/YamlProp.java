package com.phunghung29.microservice.gateway.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@PropertySource(value = "classpath:microservice.settings.yaml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties
public class YamlProp {
    private Map<String, SubServiceSetting> settings;
}
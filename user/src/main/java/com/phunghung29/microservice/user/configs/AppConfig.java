package com.phunghung29.microservice.user.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@Configuration
@EnableJpaAuditing
public class AppConfig {
    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

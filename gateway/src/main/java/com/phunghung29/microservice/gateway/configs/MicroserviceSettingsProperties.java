//package com.phunghung29.microservice.gateway.configs;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.validation.annotation.Validated;
//
//import java.util.List;
//import java.util.Map;
//
//@Configuration
//@Validated
//@PropertySource(value = "classpath:microservice.settings.yaml", factory = YamlPropertySourceFactory.class)
//@ConfigurationProperties("settings")
//@Primary
//@Getter
//@Setter
//public class MicroserviceSettingsProperties {
//    private List<SubServiceSetting> settings;
//}

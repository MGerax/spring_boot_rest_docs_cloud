package com.and;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties("spring.application")
public class ApplicationConfig {
    //@Value("${spring.application.name}")
    public String name;

    //@Value("${spring.application.version}")
    public int version;

    //@Value("${spring.application.implementations}")
    public List<String> implementations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<String> getImplementations() {
        return implementations;
    }

    public void setImplementations(List<String> implementations) {
        this.implementations = implementations;
    }
}

package com.TinkerersLab.CargoStacks.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Service
@Configuration
@ConfigurationProperties(prefix = "spring.application")
@Validated
public class ApplicationProperties {

    private int bcryptPasswordEncoderStrength=12;

    private String repository="repository";

}
package com.TinkerersLab.CargoStacks.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;


@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {


    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

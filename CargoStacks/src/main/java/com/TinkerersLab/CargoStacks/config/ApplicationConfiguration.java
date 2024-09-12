package com.TinkerersLab.CargoStacks.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ApplicationConfiguration {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ApplicationProperties applicationProperties;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(customizer -> customizer.disable())
            .httpBasic(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        httpSecurity.authorizeHttpRequests( auth -> {

            //requests open for everyone
            auth.requestMatchers(HttpMethod.GET, "/api/v1/components/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/tools/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/allocations/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/utilizations/**").permitAll();

            //users can create new allocations and utilizations
            auth.requestMatchers(HttpMethod.POST, "/api/v1/components/*/allocations").permitAll();
            auth.requestMatchers(HttpMethod.POST, "/api/v1/tools/*/utilizations").permitAll();

            //only admin can create, update, delete new components, tools and allocations
            auth.requestMatchers(HttpMethod.POST, "/api/v1/tools").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.POST, "/api/v1/components").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN");
            auth.requestMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN");
        });

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder(applicationProperties.getBcryptPasswordEncoderStrength()));
        authenticationProvider.setUserDetailsService(userDetailsService);
        return authenticationProvider;
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}

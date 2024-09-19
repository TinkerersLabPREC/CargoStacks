package com.TinkerersLab.CargoStacks.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.config.ApplicationProperties;

@Configuration
public class SecurityConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .cors(customizer -> customizer.disable())
                .csrf(customizer -> customizer.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.authorizeHttpRequests(auth -> {

            // requests open for everyone
            auth.requestMatchers(HttpMethod.GET, "/api/v1/components/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/tools/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/allocations/**").permitAll();
            auth.requestMatchers(HttpMethod.GET, "/api/v1/utilizations/**").permitAll();

            // users can create new allocations and utilizations
            auth.requestMatchers(HttpMethod.POST, "/api/v1/components/*/allocations")
                    .hasAnyRole(ApplicationConstants.ROLE_GUEST, ApplicationConstants.ROLE_ADMIN);
            auth.requestMatchers(HttpMethod.POST, "/api/v1/tools/*/utilizations")
                    .hasAnyRole(ApplicationConstants.ROLE_GUEST, ApplicationConstants.ROLE_ADMIN);

            // only admin can create, update, delete new components, tools and allocations
            auth.requestMatchers(HttpMethod.GET, "/admin/**").hasRole(ApplicationConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.POST, "/api/v1/tools/**").hasRole(ApplicationConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.POST, "/api/v1/components/**").hasRole(ApplicationConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.DELETE, "/**").hasRole(ApplicationConstants.ROLE_ADMIN)
                    .requestMatchers(HttpMethod.PUT, "/**").hasRole(ApplicationConstants.ROLE_ADMIN)
                    .anyRequest()
                    .authenticated();
        });

        httpSecurity.httpBasic(customizer -> customizer.authenticationEntryPoint(customAuthenticationEntryPoint));

        httpSecurity
                .exceptionHandling(customizer -> customizer.authenticationEntryPoint(customAuthenticationEntryPoint));

        return httpSecurity.build();
    }

    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    // DaoAuthenticationProvider authenticationProvider = new
    // DaoAuthenticationProvider();
    // authenticationProvider.setPasswordEncoder(new
    // BCryptPasswordEncoder(applicationProperties.getBcryptPasswordEncoderStrength()));
    // authenticationProvider.setUserDetailsService(userDetailsService);
    // return authenticationProvider;
    // }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(applicationProperties.getBcryptPasswordEncoderStrength());
    }
}

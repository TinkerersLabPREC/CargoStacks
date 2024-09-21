package com.TinkerersLab.CargoStacks.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.config.ApplicationProperties;
import com.TinkerersLab.CargoStacks.models.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;

@Configuration
public class SecurityConfig {

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity httpSecurity) throws Exception {

		// CORS configuration
		httpSecurity.cors(cors -> {
			CorsConfiguration config = new CorsConfiguration();

			config.addAllowedOrigin("http://localhost:3000");
			config.setAllowCredentials(true);

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);
			cors.configurationSource(source);
		});

		// CSRF and Session management configuration
		httpSecurity
				.csrf(customizer -> customizer.disable())
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Endpoint security configuration
		httpSecurity.authorizeHttpRequests(auth -> {

			// requests open for everyone
			auth.requestMatchers(HttpMethod.GET, "/api/v1/components/**").permitAll();
			auth.requestMatchers(HttpMethod.GET, "/api/v1/tools/**").permitAll();
			auth.requestMatchers(HttpMethod.GET, "/api/v1/allocations/**").permitAll();
			auth.requestMatchers(HttpMethod.GET, "/api/v1/utilizations/**").permitAll();

			// users can create new allocations and utilizations
			auth.requestMatchers(HttpMethod.POST, "/api/v1/components/*/allocations")
					.hasAnyRole(ApplicationConstants.ROLE_GUEST,
							ApplicationConstants.ROLE_ADMIN);
			auth.requestMatchers(HttpMethod.POST, "/api/v1/tools/*/utilizations")
					.hasAnyRole(ApplicationConstants.ROLE_GUEST,
							ApplicationConstants.ROLE_ADMIN);

			// only admin can create, update, delete new components, tools and allocations
			auth.requestMatchers(HttpMethod.GET, "/admin/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.POST, "/api/v1/tools/**")
					.hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.POST, "/api/v1/components/**")
					.hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.DELETE, "/**")
					.hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.PUT, "/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.anyRequest()
					.authenticated();
		});

		// Basic authentication configuration
		httpSecurity.httpBasic(
				customizer -> customizer.authenticationEntryPoint(customAuthenticationEntryPoint));

		// Exception handling configuration
		httpSecurity.exceptionHandling(customizer -> customizer
				.authenticationEntryPoint(customAuthenticationEntryPoint)
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType(MediaType.APPLICATION_JSON_VALUE);

					ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
							.message("Access Denied! " + accessDeniedException.getMessage())
							.status(HttpStatus.FORBIDDEN)
							.payload("User does not have permission to access this resource")
							.success(false)
							.build();

					String jsonString = new ObjectMapper().writeValueAsString(errorResponse);
					response.getWriter().write(jsonString);
				}));

		return httpSecurity.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(applicationProperties.getBcryptPasswordEncoderStrength());
	}

	@Bean
	public KeyGenerator keyGenerator() throws NoSuchAlgorithmException {
		KeyGenerator keyGen = KeyGenerator.getInstance("HS512");
		keyGen.init(128);
		return keyGen;
	}
}

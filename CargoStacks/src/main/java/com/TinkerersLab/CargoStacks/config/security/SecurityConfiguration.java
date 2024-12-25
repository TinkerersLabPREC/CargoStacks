package com.TinkerersLab.CargoStacks.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.models.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.KeyGenerator;

@Configuration
@EnableWebSecurity()
public class SecurityConfiguration {

	@Lazy
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();

	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity httpSecurity) throws Exception {

		// CORS configuration
		httpSecurity.cors(cors -> {
			CorsConfiguration config = new CorsConfiguration();

			config.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:5173"));
			// config.addAllowedOrigin("http://localhost:3000");
			// config.setAllowCredentials(true);

			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			source.registerCorsConfiguration("/**", config);
			cors.configurationSource(source);
		});

		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		// CSRF and Session management configuration
		httpSecurity
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// Endpoint security configuration
		httpSecurity.authorizeHttpRequests(auth -> {

			// requests open for everyone
			auth
					// .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "swagger-resources/**").permitAll()
					.requestMatchers("/api/v1/auth/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/components/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/tools/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/allocations/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/api/v1/utilizations/**").permitAll()

					// users can create new allocations and utilizations
					.requestMatchers(HttpMethod.POST, "/api/v1/components/*/allocations")
					.hasAnyRole(ApplicationConstants.ROLE_GUEST, ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.POST, "/api/v1/tools/*/utilizations")
					.hasAnyRole(ApplicationConstants.ROLE_GUEST, ApplicationConstants.ROLE_ADMIN)

					// only admin can create, update, delete new components, tools andallocations
					.requestMatchers(HttpMethod.GET, "/admin/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.POST, "/api/v1/tools/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.POST, "/api/v1/components/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.DELETE, "/**").hasRole(ApplicationConstants.ROLE_ADMIN)
					.requestMatchers(HttpMethod.PUT, "/**").hasRole(ApplicationConstants.ROLE_ADMIN);

			auth.requestMatchers("/**").permitAll().anyRequest().authenticated();

		});

		// Basic authentication configuration
		httpSecurity.httpBasic(
				customizer ->
				customizer.authenticationEntryPoint(customAuthenticationEntryPoint));

		httpSecurity.addFilterAfter(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public Key key() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		keyGenerator.init(256);
		return keyGenerator.generateKey();
	}
}

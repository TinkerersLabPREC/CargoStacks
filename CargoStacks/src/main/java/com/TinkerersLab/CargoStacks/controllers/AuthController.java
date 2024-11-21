package com.TinkerersLab.CargoStacks.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TinkerersLab.CargoStacks.dtos.JwtResponse;
import com.TinkerersLab.CargoStacks.dtos.LoginRequest;
import com.TinkerersLab.CargoStacks.helper.JwtUtil;
import com.TinkerersLab.CargoStacks.models.dao.user.UserPrincipal;
import com.TinkerersLab.CargoStacks.services.UserServiceImpl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthenticationManager authManager;

    UserDetailsService userDetailsService;

    JwtUtil jwtUtil;

    UserServiceImpl userService;

    @PostMapping("/login")
    public ResponseEntity<?> createToken(@RequestBody LoginRequest loginRequest) {
        System.out.println("Login request received");
        try {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), loginRequest.getPassword());

            authManager.authenticate(authToken);
        } catch (AuthenticationException authException) {
            throw new BadCredentialsException("Invalid username or password");
        }
        // user authenticated
        System.out.println("User got authenticated");
        UserPrincipal user = (UserPrincipal) userDetailsService.loadUserByUsername(loginRequest.getUsername());

        String token = jwtUtil.generateToken(user.getUsername());

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setJwtToken(token);
        jwtResponse.setUser(userService.entityToDto(user.getUser()));

        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> SigninNewUser(@RequestBody String entity) {
        return null;
    }

}

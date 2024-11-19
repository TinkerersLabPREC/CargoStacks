package com.TinkerersLab.CargoStacks.dtos;

import lombok.Data;

@Data
public class JwtResponse {

    private String jwtToken;

    private UserDto user;
}

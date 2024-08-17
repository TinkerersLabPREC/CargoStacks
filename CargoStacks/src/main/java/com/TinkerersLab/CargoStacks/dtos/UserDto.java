package com.TinkerersLab.CargoStacks.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 8, max = 35)
    private String username;

    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;

    @Size(min = 0, max = 100)
    private String userDescription;

}

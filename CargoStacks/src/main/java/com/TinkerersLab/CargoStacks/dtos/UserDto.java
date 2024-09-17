package com.TinkerersLab.CargoStacks.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    private String id;

    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;

    private String email;

    @Size(min = 0, max = 100)
    private String userDescription;

}

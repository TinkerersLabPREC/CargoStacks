package com.TinkerersLab.CargoStacks.dtos;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

    private String id;

    @JsonIgnore
    @NotEmpty
    @Size(min = 8, max = 50)
    private String password;

    @NotEmpty
    @Size(min = 6, max = 50)
    private String email;

    @Size(min = 0, max = 100)
    private String userDescription;

    
    @Size(min = 0, max = 50)
    private String Organization;    //college name
    
    @Size(min = 0, max = 15)
    private String contact;
    
    @Size(min = 0, max = 150)
    private String address;
    
    @Size(min = 0, max = 30)
    private String department;
    
    private List<Role> roles;

}

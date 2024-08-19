package com.TinkerersLab.CargoStacks.dtos;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComponentDto {

    private String id;

    @NotEmpty
    @Size(min = 5, max = 50)
    private String name;

    @NotEmpty
    @Size(min = 10, max = 200)
    private String description;

    @NotEmpty
    @Size(min = 5, max = 50)
    private String modelName; 

    @NotEmpty
    @Size(min = 5, max = 40)
    private String location;

    @NotEmpty
    private int total;

    @NotEmpty
    private int currentlyAvailable;

    private List<Allocation> allocations;

}

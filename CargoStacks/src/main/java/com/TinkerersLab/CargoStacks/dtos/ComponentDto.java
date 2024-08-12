package com.TinkerersLab.CargoStacks.dtos;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;

import lombok.Data;

@Data
public class ComponentDto {

    private int id;

    private String name;

    private String description;

    private String modelName; 

    private String location;

    private int total;

    private int currentlyAvailable;

    private List<Allocation> allocations;

}

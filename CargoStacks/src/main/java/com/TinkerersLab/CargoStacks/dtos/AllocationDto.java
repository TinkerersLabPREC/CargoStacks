package com.TinkerersLab.CargoStacks.dtos;

import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Beneficiary;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AllocationDto {

    private String id;

    @NotEmpty
    private Beneficiary beneficiary;

    @NotEmpty
    private int quantityTaken;

    @NotEmpty
    @Size(min = 8, max = 100)
    private String projectName;

    @NotEmpty
    private Component component;

}

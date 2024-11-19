package com.TinkerersLab.CargoStacks.dtos;

import java.util.Date;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Beneficiary;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AllocationDto {

    private String id;

    @NotNull
    private Beneficiary beneficiary;

    @Min(value = 1)
    private int quantityTaken;

    @Size(min = 8, max = 100)
    private String projectName;

    private Date allocationDate;

    @NotNull
    private boolean isReturned;

    private ComponentDto component;

}

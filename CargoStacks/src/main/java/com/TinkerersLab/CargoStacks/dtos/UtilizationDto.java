package com.TinkerersLab.CargoStacks.dtos;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilizer;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

import lombok.Data;

@Data
public class UtilizationDto {

    private String id;

    @NotNull
    private Utilizer utilizer;
    
    @FutureOrPresent
    private Date utilizationTime;

    @Min(value = 1)
    private int estimatedTimeRequired;

    private Tool tool;

}

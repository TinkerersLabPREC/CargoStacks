package com.TinkerersLab.CargoStacks.dtos;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ToolDto {

    private String id;

    @NotEmpty
    @Size(min = 8, max = 30)
    private String name;

    @NotEmpty
    @Size(min = 5, max = 50)
    private String modelName;

    @NotEmpty
    @Size(min = 10, max = 500)
    private String description;

    @Min(value = 1, message = "value could not be negative or zero")
    private int price;

    @Size(max = 100)
    private String requiredSoftware;

    private List<Utilization> utilization;

}

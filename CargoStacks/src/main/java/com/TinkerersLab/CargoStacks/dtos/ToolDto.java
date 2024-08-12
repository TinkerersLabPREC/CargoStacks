package com.TinkerersLab.CargoStacks.dtos;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;

import lombok.Data;

@Data
public class ToolDto {

    private int id;

    private String name;

    private String modelName;

    private String description;

    private int price;

    private String requiredSoftware;

    private List<Utilization> utilization;

}

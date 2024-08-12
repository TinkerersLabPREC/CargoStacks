package com.TinkerersLab.CargoStacks.dtos;

import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Beneficiary;

import lombok.Data;

@Data
public class AllocationDto {

    private int id;

    private Beneficiary beneficiary;

    private int quantityTaken;

    private String projectName;

    private Component component;

}

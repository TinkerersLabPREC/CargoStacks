package com.TinkerersLab.CargoStacks.dtos;

import java.sql.Time;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilizer;

import lombok.Data;

@Data
public class UtilizationDto {

    private int id;

    private Utilizer utilizer;
    
    private Time UtilizationTime;
    
    private int estimatedTimeRequired;

    private Tool tool;

}

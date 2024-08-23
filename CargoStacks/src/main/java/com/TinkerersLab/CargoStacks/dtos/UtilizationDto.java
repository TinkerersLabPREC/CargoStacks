package com.TinkerersLab.CargoStacks.dtos;

import java.sql.Time;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilizer;

import java.util.Date;

import lombok.Data;

@Data
public class UtilizationDto {

    private String id;

    private Utilizer utilizer;
    
    private Date UtilizationTime;
    
    private int estimatedTimeRequired;

    private Tool tool;

}

package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;

public interface UtilizationService {

    List<UtilizationDto> getAll();

    UtilizationDto getById(String id);

    UtilizationDto utilize(UtilizationDto utilizationDto);

    UtilizationDto update(UtilizationDto newUtilizationDto, String id);
    
    UtilizationDto utilizeTool(ToolDto toolDto, UtilizationDto utilizationDto);
}

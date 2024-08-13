package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;

public interface UtilizationService {

    List<UtilizationDto> getAll();

    UtilizationDto getById(String id);

    UtilizationDto utilize(UtilizationDto utilizationDto);

    UtilizationDto update(UtilizationDto newUtilizationDto, String id);

    
}

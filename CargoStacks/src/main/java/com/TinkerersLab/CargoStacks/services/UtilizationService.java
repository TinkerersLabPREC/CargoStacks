package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;

public interface UtilizationService {

    CustomPageResponse<UtilizationDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq);

    UtilizationDto getById(String id);

    UtilizationDto utilize(String toolId, UtilizationDto utilizationDto);

    UtilizationDto update(UtilizationDto newUtilizationDto, String id);

    CustomPageResponse<UtilizationDto> getUtilizationOfTool(int pageNumber, int pageSize, String sortBy, String sortSeq, String toolId);

}

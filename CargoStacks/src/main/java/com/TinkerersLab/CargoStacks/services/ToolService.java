package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.ToolDto;

public interface ToolService {

    ToolDto create(ToolDto toolDto);

    List<ToolDto> getAll();

    ToolDto getById(String id);

    ToolDto update(ToolDto toolDto, String toolId);

    ToolDto delete(String id);
    
    List<ToolDto> search(String keyword);
}

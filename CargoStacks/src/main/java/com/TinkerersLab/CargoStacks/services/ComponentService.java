package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.ComponentDto;

public interface ComponentService {

    ComponentDto create(ComponentDto componentDto);

    List<ComponentDto> getAll();

    ComponentDto getById(String componentId);

    List<ComponentDto> search(String keyword);

    ComponentDto deleteById(String componentId);

    ComponentDto update(ComponentDto newComponentDto);
}

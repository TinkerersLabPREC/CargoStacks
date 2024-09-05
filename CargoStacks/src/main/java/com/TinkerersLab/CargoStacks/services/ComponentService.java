package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;

public interface ComponentService {

    ComponentDto create(ComponentDto componentDto);

    CustomPageResponse<ComponentDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq);

    ComponentDto getById(String componentId);

    List<ComponentDto> search(String keyword);

    ComponentDto deleteById(String componentId);

    ComponentDto update(ComponentDto newComponentDto, String id);

    void saveComponentImage(MultipartFile file, String componentId);

    ResourceContentType getComponentImage(String componentId);

}

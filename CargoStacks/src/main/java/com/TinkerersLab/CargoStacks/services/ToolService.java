package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;

public interface ToolService {

    CustomPageResponse<ToolDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq);

    ToolDto create(ToolDto toolDto);

    ToolDto getById(String id);

    ToolDto update(ToolDto toolDto, String toolId);

    ToolDto delete(String id);

    List<ToolDto> search(String keyword);

    void saveToolImage(MultipartFile file, String toolId);

    ResourceContentType getToolImage(String toolId);

}

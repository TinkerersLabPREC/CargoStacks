package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.repository.ToolRepo;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolServiceImpl implements ToolService {

    ToolRepo toolRepo;

    ModelMapper modelMapper;

    public ToolServiceImpl(ToolRepo toolRepo, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.toolRepo = toolRepo;
    }

    @Override
    public ToolDto create(ToolDto toolDto) {
        Tool tool = toolRepo.save(dtoToEntity(toolDto));
        return entityToDto(tool);
    }

    @Override
    public List<ToolDto> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    public ToolDto findById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public ToolDto update(ToolDto toolDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public ToolDto delete(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public List<ToolDto> search(String keyword) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'search'");
    }

    public ToolDto entityToDto(Tool tool){
        ToolDto toolDto = modelMapper.map(tool, ToolDto.class);
        return toolDto;
    }

    public Tool dtoToEntity(ToolDto tooldDto){
        Tool tool = modelMapper.map(tooldDto, Tool.class);
        return tool;
    }


    // public List<Tool> getAllTools() {
    //     return toolRepo.findAll();
    // }

    // public Tool getToolById(String id) {
    //     return toolRepo.findById(id).orElse(new Tool());
    // }

    // public List<Tool> searchTools(String keyword) {
    //     return toolRepo.searchTools(keyword);
    // }

    // public Tool addTool(Tool tool) {
    //     return toolRepo.save(tool);
    // }

    // public Tool deleteById(String id) {
    //     Tool tool = getToolById(id);
    //     toolRepo.delete(tool);
    //     return tool;
    // }

    // public Tool updateTools(Tool tool) {
    //     return toolRepo.save(tool);
    // }
}

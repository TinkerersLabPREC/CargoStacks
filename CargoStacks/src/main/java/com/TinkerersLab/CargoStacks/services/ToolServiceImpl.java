package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
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
        List<Tool> tools = toolRepo.findAll();
        List<ToolDto> toolDtos = tools
                .stream()
                .map(tool -> entityToDto(tool))
                .toList()
        ;
        return toolDtos;
    }

    @Override
    public ToolDto findById(String id) {
        Tool tool = toolRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tool of provided id not found!" ,id));
        return entityToDto(tool);
    }

    @Override
    public ToolDto update(ToolDto toolDto) {
        toolRepo
            .findById(toolDto.getId())
            .orElseThrow(()-> new ResourceNotFoundException("Tool to be updated not found!", toolDto.getId()));

        Tool newTool = toolRepo.save(dtoToEntity(toolDto));
        return entityToDto(newTool);        
    }

    @Override
    public ToolDto delete(String id) {
        Tool tool = toolRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Tool of given id not found!", id));
        toolRepo.deleteById(id);
        return entityToDto(tool);
    }

    @Override
    public List<ToolDto> search(String keyword) {
        List<Tool> result = toolRepo.searchTools(keyword);
        return result
            .stream()
            .map(tool -> entityToDto(tool))
            .toList();
    }

    public ToolDto entityToDto(Tool tool){
        ToolDto toolDto = modelMapper.map(tool, ToolDto.class);
        return toolDto;
    }

    public Tool dtoToEntity(ToolDto toolDto){
        Tool tool = modelMapper.map(toolDto, Tool.class);
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

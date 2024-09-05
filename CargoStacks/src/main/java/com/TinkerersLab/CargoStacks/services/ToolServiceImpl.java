package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.config.ApplicationProperties;
import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.repository.ToolRepo;

import java.io.File;
import java.io.IOException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToolServiceImpl implements ToolService {

    ToolRepo toolRepo;

    ModelMapper modelMapper;

    ApplicationProperties applicationProperties;

    FileServiceImpl fileService;


    @Override
    public ToolDto create(ToolDto toolDto) {
        System.out.println(toolDto);
        toolDto.setId(UUID.randomUUID().toString());
        Tool tool = toolRepo.save(dtoToEntity(toolDto));
        return entityToDto(tool);
    }

    @Override
    public CustomPageResponse<ToolDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq) {
        if(pageNumber <= 0){
            return null;
        }

        Sort sort;
        if(sortSeq.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Tool> toolPage = toolRepo.findAll(pageRequest);
        List<Tool> tools = toolPage.getContent();

        List<ToolDto> toolDtos = tools
            .stream()
            .map(tool -> entityToDto(tool))
            .toList();

        CustomPageResponse<ToolDto> customPageResponse = CustomPageResponse
            .<ToolDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalPages(toolPage.getTotalPages())
            .totalElements(toolPage.getTotalElements())
            .content(toolDtos)
            .isLast(toolPage.isLast())
            .build();

        return customPageResponse;
    }

    @Override
    public ToolDto getById(String id) {
        Tool tool = toolRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Tool of provided id not found!" ,id));
        return entityToDto(tool);
    }

    @Override
    public ToolDto update(ToolDto toolDto, String toolId) {
        Tool oldTool = toolRepo
            .findById(toolId)
            .orElseThrow(()-> new ResourceNotFoundException("Tool to be updated not found!", toolId));

        toolDto.setId(toolId);

        toolRepo.save(dtoToEntity(toolDto));
        return entityToDto(oldTool);        
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

    @Override
    public void saveToolImage(MultipartFile file, String toolId) {
        Tool tool = toolRepo
            .findById(toolId)
            .orElseThrow(() -> new ResourceNotFoundException("Tool with provided id not found!", toolId));

        String path = applicationProperties.getRepository() + 
        File.separator + "tools" + 
        File.separator + toolId;

        String imagePath;

        try {
            imagePath = fileService.saveFile(file, path);
        } catch (IOException e) {
            throw new RuntimeException("Could not save image file");
        }

        tool.setImage(new com.TinkerersLab.CargoStacks.models.File());
        tool.getImage().setPath(imagePath);
        tool.getImage().setContentType(file.getContentType());
        toolRepo.save(tool);
    }



    @Override
    public ResourceContentType getToolImage(String toolId) {
        
        Tool tool = toolRepo
            .findById(toolId)
            .orElseThrow(() -> new ResourceNotFoundException("Tool with provided id not found", toolId));
        
        ResourceContentType resourceContentType = fileService.getFile(tool.getImage().getPath());
        resourceContentType.setContentType(tool.getImage().getContentType());
    
        return resourceContentType;


    }

    public ToolDto entityToDto(Tool tool){
        ToolDto toolDto = modelMapper.map(tool, ToolDto.class);
        return toolDto;
    }

    public Tool dtoToEntity(ToolDto toolDto){
        return modelMapper.map(toolDto, Tool.class);
    }


}

package com.TinkerersLab.CargoStacks.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;
import com.TinkerersLab.CargoStacks.repository.UtilizationRepo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilizationServiceImpl implements UtilizationService {

    private UtilizationRepo utilizationRepo;

    private ModelMapper modelMapper;

    private ToolServiceImpl toolService;

    public CustomPageResponse<UtilizationDto> getUtilizationOfTool(int pageNumber, int pageSize, String sortBy, String sortSeq, String toolId) {

        Sort sort;

        if(sortSeq.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }

        Tool tool = toolService.dtoToEntity(toolService.getById(toolId));

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Utilization> utilizationPage = utilizationRepo.findByTool(tool, pageRequest);
        List<Utilization> utilizations = utilizationPage.getContent();

        List<UtilizationDto> utilizationDtos = utilizations
            .stream()
            .map( utilization ->  entityToDto(utilization))
            .toList();

        return CustomPageResponse
            .<UtilizationDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalElements(utilizationPage.getTotalElements())
            .totalPages(utilizationPage.getTotalPages())
            .content(utilizationDtos)
            .isLast(utilizationPage.isLast())
            .build();
    }

    @Override 
    public UtilizationDto utilize(String toolId, UtilizationDto newUtilizationDto) {

        Tool tool = toolService.dtoToEntity(toolService.getById(toolId));

        newUtilizationDto.setId(UUID.randomUUID().toString());
        
        if(newUtilizationDto.getUtilizationTime() == null){
            newUtilizationDto.setUtilizationTime(new Date());
        }else{
            newUtilizationDto.setUtilizationTime(newUtilizationDto.getUtilizationTime());
        }
        
        Utilization newUtilization = dtoToEntity(newUtilizationDto);
        newUtilization.setTool(tool);

        Utilization savedUtilization = utilizationRepo.save(newUtilization);
        return entityToDto(savedUtilization);
    }

    @Override
    public CustomPageResponse<UtilizationDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq) {
        
        Sort sort;
        if(sortSeq.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }

        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Utilization> utilizationPage = utilizationRepo.findAll(pageRequest);
        List<Utilization> utilizations = utilizationPage.getContent();

        List<UtilizationDto> utilizationsDtos = utilizations
            .stream()
            .map(utilization -> entityToDto(utilization))
            .toList();
        
        return CustomPageResponse
            .<UtilizationDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalPages(utilizationPage.getTotalPages())
            .totalElements(utilizationPage.getTotalElements())
            .content(utilizationsDtos)
            .isLast(utilizationPage.isLast())
            .build();
    }

    @Override
    public UtilizationDto getById(String id) {
        
        Utilization utilization = utilizationRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Utilization with provided id not found", id));

        return entityToDto(utilization);
    }

    @Override
    public UtilizationDto update(UtilizationDto newUtilizationDto, String id) {
        Utilization oldUtilization = utilizationRepo.findById(id).get();

        oldUtilization.setEstimatedTimeRequired(newUtilizationDto.getEstimatedTimeRequired());
        oldUtilization.setUtilizer(newUtilizationDto.getUtilizer());

        Utilization savedUtilization = utilizationRepo.save(oldUtilization);

        return entityToDto(savedUtilization);
    }

    public UtilizationDto entityToDto(Utilization utilization){
        return modelMapper.map(utilization, UtilizationDto.class);
    }

    public Utilization dtoToEntity(UtilizationDto utilizationDto){
        return modelMapper.map(utilizationDto, Utilization.class);
    }

}

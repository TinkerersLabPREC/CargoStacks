package com.TinkerersLab.CargoStacks.services;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;
import com.TinkerersLab.CargoStacks.repository.UtilizationRepo;

@Service
public class UtilizationServiceImpl implements UtilizationService {

    private UtilizationRepo utilizationRepo;

    private ModelMapper modelMapper;

    private ToolServiceImpl toolService;

    public UtilizationServiceImpl(UtilizationRepo utilizationRepo, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.utilizationRepo = utilizationRepo;
    }


    @Override
    public List<UtilizationDto> getAll() {
        List<Utilization> utilizations = utilizationRepo.findAll();
        return utilizations
            .stream()
            .map(utilization -> entityToDto(utilization))
            .toList();
    }


    @Override
    public UtilizationDto getById(String id) {
        Utilization utilization = utilizationRepo.findById(id).get();
        return entityToDto(utilization);
    }

    @Override
    public UtilizationDto utilize(UtilizationDto utilizationDto) {
        utilizationDto.setId(UUID.randomUUID().toString());
        Utilization utilization = utilizationRepo.save(dtoToEntity(utilizationDto));
        return entityToDto(utilization);
    }

    @Override
    public UtilizationDto update(UtilizationDto newUtilizationDto, String id) {
        Utilization oldUtilization = utilizationRepo.findById(id).get();
        utilizationRepo.save(dtoToEntity(newUtilizationDto));
        return entityToDto(oldUtilization);
    }

    @Override
    public UtilizationDto utilizeTool(ToolDto toolDto, UtilizationDto newUtilizationDto) {
        Tool tool = toolService.dtoToEntity(toolDto);
        Utilization newUtilization = dtoToEntity(newUtilizationDto);

        tool.getUtilization().add(newUtilization);
        Utilization savedUtilization =  utilizationRepo.save(newUtilization);

        return entityToDto(savedUtilization);
    }

    public UtilizationDto entityToDto(Utilization utilization){
        return modelMapper.map(utilization, UtilizationDto.class);
    }

    public Utilization dtoToEntity(UtilizationDto utilizationDto){
        return modelMapper.map(utilizationDto, Utilization.class);
    }

}

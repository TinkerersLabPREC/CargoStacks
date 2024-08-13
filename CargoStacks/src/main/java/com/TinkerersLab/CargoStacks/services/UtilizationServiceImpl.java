package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;
import com.TinkerersLab.CargoStacks.repository.UtilizationRepo;

@Service
public class UtilizationServiceImpl implements UtilizationService {

    private UtilizationRepo utilizationRepo;

    private ModelMapper modelMapper;


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
        Utilization utilization = utilizationRepo.save(dtoToEntity(utilizationDto));
        return entityToDto(utilization);
    }

    @Override
    public UtilizationDto update(UtilizationDto newUtilizationDto, String id) {
        Utilization oldUtilization = utilizationRepo.findById(id).get();
        utilizationRepo.save(dtoToEntity(newUtilizationDto));
        return entityToDto(oldUtilization);
    }

    public UtilizationDto entityToDto(Utilization utilization){
        UtilizationDto utilizationDto = modelMapper.map(utilization, UtilizationDto.class);
        return utilizationDto;
    }

    public Utilization dtoToEntity(UtilizationDto utilizationDto){
        Utilization utilization = modelMapper.map(utilizationDto, Utilization.class);
        return utilization;
    }

}

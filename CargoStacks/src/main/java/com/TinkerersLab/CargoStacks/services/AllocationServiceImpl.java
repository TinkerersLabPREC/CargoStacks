package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;
import com.TinkerersLab.CargoStacks.repository.AllocationRepo;

public class AllocationServiceImpl implements AllocationService {

    private AllocationRepo allocationRepo;
    
    private ModelMapper modelMapper;

    public AllocationServiceImpl(AllocationRepo allocationRepo, ModelMapper modelMapper){
        this.allocationRepo = allocationRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public AllocationDto allocate(AllocationDto allocationDto) {

        Allocation allocation = allocationRepo.save(dtoToEntity(allocationDto));
        return entityToDto(allocation);
    }

    @Override
    public AllocationDto deallocate(AllocationDto allocationDto, String id) {
        Allocation allocation = allocationRepo.findById(id).get();
        allocationRepo.delete(allocation);
        return entityToDto(allocation);
    }

    @Override
    public AllocationDto updateAllocation(AllocationDto allocationDto, String id) {
        Allocation allocation = allocationRepo.findById(id).get();
        allocationRepo.save(dtoToEntity(allocationDto));
        return entityToDto(allocation);
    }

    @Override
    public AllocationDto getById(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public List<AllocationDto> getAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    public AllocationDto entityToDto(Allocation allocation){
        AllocationDto allocationDto = modelMapper.map(allocation, AllocationDto.class);
        return allocationDto;
    }

    public Allocation dtoToEntity(AllocationDto allocationDto){
        Allocation allocation = modelMapper.map(allocationDto, Allocation.class);
        return allocation;
    }

}

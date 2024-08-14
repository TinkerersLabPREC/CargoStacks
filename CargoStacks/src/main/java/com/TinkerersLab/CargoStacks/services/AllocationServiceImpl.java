package com.TinkerersLab.CargoStacks.services;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
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
        allocationDto.setId(UUID.randomUUID().toString());
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
        Allocation allocation = allocationRepo.findById(id).get();
        return entityToDto(allocation);
    }

    @Override
    public List<AllocationDto> getAll() {
        List<Allocation> allocations = allocationRepo.findAll();
        return allocations
            .stream()
            .map( allocation -> entityToDto(allocation))
            .toList();
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

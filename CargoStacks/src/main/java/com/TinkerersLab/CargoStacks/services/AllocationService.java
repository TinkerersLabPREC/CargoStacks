package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;

public interface AllocationService {

    AllocationDto allocate(String componentId, AllocationDto allocationDto);

    AllocationDto deallocate(String componentId, String allocationId);

    AllocationDto updateAllocation(AllocationDto allocationDto, String id);

    AllocationDto getById(String id);
    
    CustomPageResponse<AllocationDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq);

    CustomPageResponse<AllocationDto> getAllOfComponent(String componentId, int pageNumber, int pageSize, String sortBy, String sortSeq);
    
}

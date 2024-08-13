package com.TinkerersLab.CargoStacks.services;

import java.util.List;

import com.TinkerersLab.CargoStacks.dtos.AllocationDto;

public interface AllocationService {

    AllocationDto allocate(AllocationDto allocationDto);

    AllocationDto deallocate(AllocationDto allocationDto);

    AllocationDto updateAllocation(AllocationDto allocationDto, String id);

    List<AllocationDto> getAll();

    

    
}

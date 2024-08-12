package com.TinkerersLab.CargoStacks.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;
import com.TinkerersLab.CargoStacks.repository.AllocationRepo;

public class AllocationServiceImpl {

    @Autowired
    private AllocationRepo allocationRepo;
    
    @Autowired
    private ComponentsServiceImpl componentsService;

    // public Allocation allocate(Allocation allocation, int compId){
    //     Component component = componentsService.getComponentById(compId);
    //     component.setCurrentlyAvailable(component.getCurrentlyAvailable() - allocation.getQuantityTaken());
    //     allocation.setComponent(component);
    //     return allocationRepo.save(allocation);
    // }
}

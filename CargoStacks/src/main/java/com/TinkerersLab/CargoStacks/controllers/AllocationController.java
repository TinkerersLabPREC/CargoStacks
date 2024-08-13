package com.TinkerersLab.CargoStacks.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;

@RestController
@RequestMapping("/api/v1/allocations")
public class AllocationController {


    @PostMapping("/{id}/allocate")
    public ResponseEntity<Allocation> allocateComponent(@RequestBody Allocation allocation, @PathVariable("id") int compId) {
        
        return new ResponseEntity<>(null);
    }

    @PostMapping("/{componentId}/{allocationId}")
    public ResponseEntity<Allocation> returnAllocated(@PathVariable("componentId")int componentId, 
        @PathVariable("allocationId") int allocationId
    ){
        return null;

    }
}

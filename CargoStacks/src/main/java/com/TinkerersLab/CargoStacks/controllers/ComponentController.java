package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.services.ComponentsServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/components")
public class ComponentController {

    @Autowired
    private ComponentsServiceImpl componentsService;

    @GetMapping
    public List<ComponentDto> getMethodName() {
        return componentsService.getAll();
    }
    
    @GetMapping("/{componentId}")
    public ComponentDto getMethodName(@PathVariable String componentId) {
        return componentsService.getById(componentId);
    }

    @PostMapping
    public ResponseEntity<ComponentDto> createComponent (@RequestBody ComponentDto componentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(componentsService.create(componentDto));
    }

    @PutMapping("/{componentId}")
    public ComponentDto updateComponent (@PathVariable String componentId, @RequestBody ComponentDto componentDto) {
        return componentsService.update(componentDto, componentId);
    }

    @DeleteMapping("/{componentId}")    
    public ComponentDto deleteComponent ( @PathVariable String componentId){
        return componentsService.deleteById(componentId);
    }

}

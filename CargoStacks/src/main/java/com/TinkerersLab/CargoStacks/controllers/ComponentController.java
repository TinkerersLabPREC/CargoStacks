package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.services.ComponentsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/components")
public class ComponentController {

    @Autowired
    private ComponentsServiceImpl componentsService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<ComponentDto>> getAllComponents(
        @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
        @RequestParam(name = "pageSize" , required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE ) int pageSize,
        @RequestParam(name = "sortBy", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_BY) String sortBy,
        @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq) {
        
        return ResponseEntity.status(HttpStatus.OK).body(componentsService.getAll(pageNumber, pageSize, sortBy, sortSeq));
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

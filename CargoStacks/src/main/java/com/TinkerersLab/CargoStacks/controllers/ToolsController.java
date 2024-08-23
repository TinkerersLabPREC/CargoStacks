package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.services.ToolServiceImpl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/tools")
@CrossOrigin
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToolsController {

    ToolServiceImpl toolService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<ToolDto>> getAllTools(
        @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
        @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE ) int pageSize,
        @RequestParam(name = "sortBy", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_BY) String sortBy,
        @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(toolService.getAll(pageNumber, pageSize, sortBy, sortSeq));
    }
    
    @GetMapping("/{toolId}")
    public ToolDto getToolByID (@RequestParam String toolId ) {
        return toolService.getById(toolId);
    }

    @PostMapping()
    public ResponseEntity<ToolDto> crateTool(@RequestBody ToolDto newTool ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toolService.create(newTool));
    }

    @PutMapping("/{toolId}")
    public ToolDto updateTool (@PathVariable String toolId, @RequestBody ToolDto updatedTool ) {
        return toolService.update(updatedTool, toolId);
    }

    @DeleteMapping("/{toolId}")
    public ToolDto deleteTool(@PathVariable String toolId){
        return toolService.delete(toolId);        
    }

    @GetMapping("/{toolId}/utilizations")
    public ResponseEntity<CustomPageResponse<UtilizationDto>> getUtilizationById(
        @PathVariable String toolId,
        @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
        @RequestParam(name = "pageSize" , required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE ) int pageSize,
        @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
        @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq
    ){
        return ResponseEntity.ok(toolService.getUtilizations(pageNumber, pageSize, sortBy, sortSeq, toolId));
    }
    
    @PostMapping("/{toolId}/utilization")
    public ResponseEntity<UtilizationDto> createUtilization (@PathVariable String toolId,
        @Valid @RequestBody UtilizationDto utilizationDto) {
        
        return ResponseEntity.status(HttpStatus.CREATED).body(toolService.utilizeTool(toolId, utilizationDto));
    }
    

}

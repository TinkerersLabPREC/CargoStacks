package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.services.ToolServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/v1/tools")
@CrossOrigin
public class ToolsController {

    @Autowired
    private ToolServiceImpl toolService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<ToolDto>> getAllTools(
        @RequestParam(name = "pageNumber", required = true, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER)
    ) {
        return 
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

}

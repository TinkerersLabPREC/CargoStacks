package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.ToolDto;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;
import com.TinkerersLab.CargoStacks.services.ToolServiceImpl;
import com.TinkerersLab.CargoStacks.services.UtilizationServiceImpl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/tools")
@CrossOrigin
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ToolsController {

    ToolServiceImpl toolService;

    UtilizationServiceImpl utilizationService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<ToolDto>> getAllTools(
            @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(toolService.getAll(pageNumber, pageSize, sortBy, sortSeq));
    }

    @GetMapping("/{toolId}")
    public ToolDto getToolByID(@PathVariable String toolId) {
        return toolService.getById(toolId);
    }

    @PostMapping()
    public ResponseEntity<ToolDto> crateTool(@RequestBody ToolDto newTool) {

        return ResponseEntity.status(HttpStatus.CREATED).body(toolService.create(newTool));
    }

    @PutMapping("/{toolId}")
    public ToolDto updateTool(@PathVariable String toolId, @Valid @RequestBody ToolDto updatedTool) {
        return toolService.update(updatedTool, toolId);
    }

    @DeleteMapping("/{toolId}")
    public ToolDto deleteTool(@PathVariable String toolId) {
        return toolService.delete(toolId);
    }

    @GetMapping("/{toolId}/utilizations")
    public ResponseEntity<CustomPageResponse<UtilizationDto>> getUtilizationById(
            @PathVariable String toolId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq
    ) {

        return ResponseEntity.ok(utilizationService.getUtilizationOfTool(pageNumber, pageSize, sortBy, sortSeq, toolId));
    }

    @PostMapping("/{toolId}/images")
    public ResponseEntity<String> saveImage(@PathVariable String toolId,
                                            @RequestParam("image") MultipartFile imageFile) {

        toolService.saveToolImage(imageFile, toolId);
        return ResponseEntity.status(HttpStatus.OK).body("file saved successfully");
    }

    @GetMapping("{toolId}/images")
    public ResponseEntity<Resource> getImage(@PathVariable String toolId) {
        ResourceContentType resourceContentType = toolService.getToolImage(toolId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(resourceContentType.getContentType()))
                .body(resourceContentType.getResource());
    }

    @PostMapping("/{toolId}/utilizations")
    public ResponseEntity<UtilizationDto> createUtilization(@PathVariable String toolId,
                                                            @Valid @RequestBody UtilizationDto utilizationDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(utilizationService.utilize(toolId, utilizationDto));
    }

}

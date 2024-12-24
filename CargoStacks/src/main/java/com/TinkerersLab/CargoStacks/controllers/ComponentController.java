package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;
import com.TinkerersLab.CargoStacks.services.AllocationServiceImpl;
import com.TinkerersLab.CargoStacks.services.ComponentsServiceImpl;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/components")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComponentController {

    ComponentsServiceImpl componentsService;

    AllocationServiceImpl allocationService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<ComponentDto>> getAllComponents(
            @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(componentsService.getAll(pageNumber, pageSize, sortBy, sortSeq));
    }

    @GetMapping("/{componentId}")
    public ComponentDto getComponentById(@PathVariable String componentId) {
        return componentsService.getById(componentId);
    }

    @PostMapping
    public ResponseEntity<ComponentDto> createComponent(@Valid @RequestBody ComponentDto componentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(componentsService.create(componentDto));
    }

    @PutMapping("/{componentId}")
    public ComponentDto updateComponent(@PathVariable String componentId, @RequestBody ComponentDto componentDto) {
        return componentsService.update(componentDto, componentId);
    }

    @DeleteMapping("/{componentId}")
    public ComponentDto deleteComponent(@PathVariable String componentId) {
        return componentsService.deleteById(componentId);
    }

    @PostMapping("/{componentId}/images")
    public ResponseEntity<String> saveImage(@PathVariable String componentId,
            @RequestParam("image") MultipartFile imageFile) {

        componentsService.saveComponentImage(imageFile, componentId);
        return ResponseEntity.status(HttpStatus.OK).body("file saved successfully");
    }

    @GetMapping("/{componentId}/images/{index}")
    public ResponseEntity<Resource> getImage(@PathVariable String componentId, @PathVariable int idx) {
        ResourceContentType resourceContentType = componentsService.getComponentImage(componentId, idx);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(resourceContentType.getContentType()))
                .body(resourceContentType.getResource());
    }

    @GetMapping("/{componentId}/allocations")
    public ResponseEntity<CustomPageResponse<AllocationDto>> getAllAllocations(
            @PathVariable String componentId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq,
            @RequestParam(name = "returned", required = false, defaultValue = ApplicationConstants.DEFAULT_ALLOCATION_RETURNED) String returned,
            @RequestParam(name = "beneficiaryName", required = false, defaultValue = ApplicationConstants.DEFAULT_BENEFICIARY_NAME) String beneficiaryName) {

        return ResponseEntity.ok(allocationService.getAllOfComponent(componentId, pageNumber, pageSize, sortBy, sortSeq,
                returned, beneficiaryName));
    }

    @PostMapping("/{componentId}/allocations")
    public ResponseEntity<AllocationDto> createAllocation(
            @PathVariable String componentId,
            @Valid @RequestBody AllocationDto newAllocation) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allocationService.allocate(componentId, newAllocation));
    }

    @DeleteMapping("/{componentId}/allocations/{allocationId}")
    public AllocationDto deallocationComponent(@PathVariable String componentId,
            @PathVariable String allocationId) {

        return allocationService.deallocate(componentId, allocationId);
    }

}
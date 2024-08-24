package com.TinkerersLab.CargoStacks.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.UtilizationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.services.UtilizationServiceImpl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


@RestController
@RequestMapping("/api/v1/utilizations")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilizationController {
    
    UtilizationServiceImpl utilizationService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<UtilizationDto>>  getAll (
        @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
        @RequestParam(name = "pageSize" , required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE ) int pageSize,
        @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
        @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq) {
        
        return ResponseEntity.ok().body(utilizationService.getAll(pageNumber, pageSize, sortBy, sortSeq));
    }

    @GetMapping("/{utilizationId}")
    public UtilizationDto getAllocationById (@PathVariable String utilizationId ) {
        return utilizationService.getById(utilizationId);
    }
}

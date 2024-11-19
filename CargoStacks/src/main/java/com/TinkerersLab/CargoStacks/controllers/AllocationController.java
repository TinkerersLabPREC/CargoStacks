package com.TinkerersLab.CargoStacks.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.TinkerersLab.CargoStacks.config.ApplicationConstants;
import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.services.AllocationServiceImpl;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/v1/allocations")
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllocationController {

    AllocationServiceImpl allocationService;

    @GetMapping
    public ResponseEntity<CustomPageResponse<AllocationDto>> getAll(
            @RequestParam(name = "pageNumber", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER) int pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "sortSeq", required = false, defaultValue = ApplicationConstants.DEFAULT_SORT_SEQ) String sortSeq) {

        return ResponseEntity.ok().body(allocationService.getAll(pageNumber, pageSize, sortBy, sortSeq));
    }

    @GetMapping("/{allocationId}")
    public AllocationDto getAllocationById(@PathVariable String allocationVariable) {
        return allocationService.getById(allocationVariable);
    }

}

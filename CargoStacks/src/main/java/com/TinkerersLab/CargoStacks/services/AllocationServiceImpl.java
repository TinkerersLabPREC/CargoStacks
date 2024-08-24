package com.TinkerersLab.CargoStacks.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.dtos.AllocationDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;
import com.TinkerersLab.CargoStacks.repository.AllocationRepo;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AllocationServiceImpl implements AllocationService {

    AllocationRepo allocationRepo;
    
    ComponentsRepo componentsRepo;

    ModelMapper modelMapper;

    @Override
    public AllocationDto allocate(String componentId, AllocationDto newAllocationDto) {
        //TODO: write a custom validator to validate allocationDto.quantity < component.currentlyAvailable
        newAllocationDto.setId(UUID.randomUUID().toString());
        Component component = componentsRepo
            .findById(componentId)
            .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));

        component.setCurrentlyAvailable(component.getCurrentlyAvailable() - newAllocationDto.getQuantityTaken());
        
        Allocation newAllocation = dtoToEntity(newAllocationDto);
        newAllocation.setReturned(false);
        newAllocation.setAllocationDate(new Date());
        newAllocation.setComponent(component);
        // newAllocation.addComponent(component);
        Allocation savedAllocation = allocationRepo.save(newAllocation);
        
        return entityToDto(savedAllocation);
    }

    @Override
    public AllocationDto deallocate(String componentId, String allocationId) {

        Component component = componentsRepo
            .findById(componentId)
            .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));

        Allocation allocation = allocationRepo
            .findById(allocationId)
            .orElseThrow(() -> new ResourceNotFoundException("Allocation with provided id not found", allocationId));

        allocation.setReturned(true);
        component.setCurrentlyAvailable(component.getCurrentlyAvailable()+allocation.getQuantityTaken());
        allocationRepo.save(allocation);
        return entityToDto(allocation);
        // Allocation allocation = allocationRepo.findById(id).get();
        // allocationRepo.delete(allocation);
        // return entityToDto(allocation);
    }

    @Override
    public AllocationDto updateAllocation(AllocationDto allocationDto, String id) {
        Allocation allocation = allocationRepo.findById(id).get();
        allocationRepo.save(dtoToEntity(allocationDto));
        return entityToDto(allocation);
    }

    @Override
    public AllocationDto getById(String id) {
        Allocation allocation = allocationRepo
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Allocation with provided id not found", id));
        return entityToDto(allocation);
    }

    @Override
    public CustomPageResponse<AllocationDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq) {
        
        Sort sort;

        if(sortBy.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Allocation> allocationPage = allocationRepo.findAll(pageRequest);
        List<Allocation> allocations = allocationPage.getContent();
        List<AllocationDto> allocationDtos = allocations
            .stream()
            .map( allocation -> entityToDto(allocation))
            .toList();
        
        return CustomPageResponse
            .<AllocationDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalElements(allocationPage.getTotalElements())
            .totalPages(allocationPage.getTotalPages())
            .isLast(allocationPage.isLast())
            .content(allocationDtos)
            .build();
    }


    @Override
    public CustomPageResponse<AllocationDto> getAllOfComponent(String componentId, 
        int pageNumber, 
        int pageSize, 
        String sortBy, 
        String sortSeq, 
        String returned, 
        String beneficiaryName) {
        
        Sort sort;

        if(sortBy.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);

        Component component = componentsRepo
            .findById(componentId)
            .orElseThrow(() -> new ResourceNotFoundException("Component with provided id does not exist", componentId));

        Page<Allocation> allocationPage;

        if(!returned.equals("null") && !beneficiaryName.equals("null")){
            // TODO: Write a custom query in repository
            allocationPage = allocationRepo.findByComponent(component, pageRequest);

        }else if(returned.equals("null") && !beneficiaryName.equals("null")){
            allocationPage = allocationRepo.findByBeneficiaryNameContainingIgnoreCaseAndComponent(beneficiaryName, component, pageRequest);

        }else if(!returned.equals("null") && beneficiaryName.equals("null")){

            if(returned.equals("true")){
                allocationPage = allocationRepo.findByComponentAndReturned(component, true, pageRequest);

            }else if(returned.equals("false")){
                allocationPage = allocationRepo.findByComponentAndReturned(component, false, pageRequest);

            }else{
                allocationPage = allocationRepo.findByComponent(component, pageRequest); 
            }
        }else {
            allocationPage = allocationRepo.findByComponent(component, pageRequest);
        }
        List<Allocation> allocations = allocationPage.getContent();
        List<AllocationDto> allocationDtos = allocations
            .stream()
            .map( allocation -> entityToDto(allocation))
            .toList();
        
        return CustomPageResponse
            .<AllocationDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalElements(allocationPage.getTotalElements())
            .totalPages(allocationPage.getTotalPages())
            .isLast(allocationPage.isLast())
            .content(allocationDtos)
            .build();
    }

    public AllocationDto entityToDto(Allocation allocation){
        return modelMapper.map(allocation, AllocationDto.class);
    }

    public Allocation dtoToEntity(AllocationDto allocationDto){
        return modelMapper.map(allocationDto, Allocation.class);
    }

}

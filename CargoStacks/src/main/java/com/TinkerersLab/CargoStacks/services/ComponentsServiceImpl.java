package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComponentsServiceImpl implements ComponentService {
  
    ComponentsRepo componentsRepo;

    ModelMapper modelMapper;

    @Override
    public ComponentDto create(ComponentDto componentDto) {
        componentDto.setId(UUID.randomUUID().toString());
        Component newComponent = componentsRepo.save(dtoToEntity(componentDto));
        return entityToDto(newComponent);
    }

    @Override
    public CustomPageResponse<ComponentDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq) {
        if(pageNumber <= 0 ){
            return null;
        }

        Sort sort;
        if(sortBy.equals("descending")){
            sort = Sort.by(sortBy).descending();
        }else{
            sort = Sort.by(sortBy).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber-1, pageSize, sort);
        Page<Component> componentPage = componentsRepo.findAll(pageRequest);
        List<Component> components = componentPage.getContent();

        List<ComponentDto> componentDtos = components
            .stream()
            .map( course -> entityToDto(course))
            .toList();
        
        CustomPageResponse<ComponentDto> customPageResponse = CustomPageResponse
            .<ComponentDto>builder()
            .pageNumber(pageNumber)
            .pageSize(pageSize)
            .totalPages(componentPage.getTotalPages())
            .totalElements(componentPage.getTotalElements())
            .isLast(componentPage.isLast())
            .content(componentDtos)
            .build();

        return customPageResponse;
    }

    @Override
    public ComponentDto getById(String componentId) {
        Component component = componentsRepo.findById(componentId).get();
        
        return entityToDto(component);
    }

    @Override
    public List<ComponentDto> search(String keyword) {
        List<Component> result = componentsRepo.searchComponent(keyword);
        return result
            .stream()
            .map(components -> entityToDto(components))
            .toList();
    }

    @Override
    public ComponentDto deleteById(String componentId) {
        Component component = componentsRepo
            .findById(componentId)
            .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found !!", componentId));
        componentsRepo.delete(component);
        return entityToDto(component);
    }

    @Override
    public ComponentDto update(ComponentDto newComponentDto, String componentId) {
        Component oldComponent = componentsRepo
            .findById(componentId)
            .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));
    
        oldComponent.setCurrentlyAvailable(newComponentDto.getCurrentlyAvailable());
        oldComponent.setDescription(newComponentDto.getDescription());
        oldComponent.setName(newComponentDto.getName());
        oldComponent.setLocation(newComponentDto.getLocation());
        oldComponent.setModelName(newComponentDto.getLocation());
        oldComponent.setTotal(newComponentDto.getTotal());

        componentsRepo.save(oldComponent);
        return entityToDto(oldComponent);

    }

    public Component dtoToEntity(ComponentDto componentDto){
        return modelMapper.map(componentDto, Component.class);
    }

    public ComponentDto entityToDto(Component component){
        return modelMapper.map(component, ComponentDto.class);
    }

}

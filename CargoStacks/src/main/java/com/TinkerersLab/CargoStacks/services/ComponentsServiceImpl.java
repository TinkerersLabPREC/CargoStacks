package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ComponentsServiceImpl implements ComponentService {
  
    private final ComponentsRepo componentsRepo;

    private final ModelMapper modelMapper;

    public ComponentsServiceImpl(ComponentsRepo componentsRepo, ModelMapper modelMapper ){
        this.componentsRepo = componentsRepo;
        this.modelMapper = modelMapper;
    }

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
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);
        Page<Component> componentPage = componentsRepo.findAll(pageRequest);
        List<Component> components = componentPage.getContent();

        List<ComponentDto> componentDtos = components
            .stream()
            .map( course -> entityToDto(course))
            .toList();
        
        CustomPageResponse<ComponentDto> customPageResponse = new CustomPageResponse<>();
        customPageResponse.setPageNumber(pageNumber);
        customPageResponse.setPageSize(pageSize);
        customPageResponse.setTotalElements(componentPage.getTotalElements());
        customPageResponse.setTotalPages(componentPage.getTotalPages());
        customPageResponse.setLast(componentPage.isLast());
        customPageResponse.setContent(componentDtos);

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
                .toList()
        ;
    }

    @Override
    public ComponentDto deleteById(String componentId) {
        Component component = componentsRepo
                .findById(componentId)
                .orElseThrow(() -> new ResourceNotFoundException("Component to be deleted not found !!", componentId));

        componentsRepo.delete(component);
        return entityToDto(component);
    }

    @Override
    public ComponentDto update(ComponentDto newComponentDto, String componentId) {
        Component oldComponent = componentsRepo.findById(componentId).get();
        componentsRepo.save(dtoToEntity(newComponentDto));
        return entityToDto(oldComponent);

    }

    public Component dtoToEntity(ComponentDto componentDto){
        return modelMapper.map(componentDto, Component.class);
    }

    public ComponentDto entityToDto(Component component){
        return modelMapper.map(component, ComponentDto.class);
    }


}

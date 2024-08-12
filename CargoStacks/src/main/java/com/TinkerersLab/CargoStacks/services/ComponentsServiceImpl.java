package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;
import com.TinkerersLab.CargoStacks.repository.AllocationRepo;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ComponentsServiceImpl implements ComponentService {
  
    private ComponentsRepo componentsRepo;

    private ModelMapper modelMapper;

    public ComponentsServiceImpl(ComponentsRepo componentsRepo, ModelMapper modelMapper ){
        this.componentsRepo = componentsRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public ComponentDto create(ComponentDto componentDto) {

        Component newComponent = componentsRepo.save(dtoToEntity(componentDto));
        return entityToDto(newComponent);
    }

    @Override
    public List<ComponentDto> getAll() {

        List<Component> components = componentsRepo.findAll();

        List<ComponentDto> componentDtos =  components
            .stream()
            .map( course -> entityToDto(course))
            .toList()
        ;
        return componentDtos;
    }

    @Override
    public ComponentDto getById(String componentId) {
        Component component = componentsRepo.findById(componentId).get();
        
        return entityToDto(component);
    }

    @Override
    public List<ComponentDto> search(String keyword) {
        List<Component> result = componentsRepo.searchComponent(keyword);
        List<ComponentDto> componentDtos = result
                .stream()
                .map(components -> entityToDto(components))
                .toList()
        ;

        return componentDtos;
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
    public ComponentDto update(ComponentDto newComponentDto) {
        Component comp = componentsRepo.save(dtoToEntity(newComponentDto));
        return entityToDto(comp);
    }

    public Component dtoToEntity(ComponentDto componentDto){
        Component component = modelMapper.map(componentDto, Component.class);
        return component;
    }

    public ComponentDto entityToDto(Component component){
        ComponentDto componentDto = modelMapper.map(component, ComponentDto.class);
        return componentDto;
    }

    // public Component getComponentById(int id) {
    //     Component component = componentsRepo.findById(id).get();
    //     if(component == null){
    //         throw new ResourceNotFoundException("Component resource with specified id not found" , id);
    //     }
    //     return component;
    // }

    // public List<Component> getAllComponents() {
    //     return componentsRepo.findAll();
    // }

    // public List<Component> searchComponent(String keyword) {
    //     return componentsRepo.searchComponent(keyword);
    // }

    // public Component addComponent(Component component) {

    //     return componentsRepo.save(component);
    // }

    // public Component deleteById(int id) {
    //     Component component = getComponentById(id);
    //     componentsRepo.deleteById(id);
    //     return component;
    // }

    // public Component updateComponent(Component component) {
    //     return componentsRepo.save(component);
    // }

    // public Allocation allocate(Allocation allocation, int compId){
    //     Component component = getComponentById(compId);
    //     component.setCurrentlyAvailable(component.getCurrentlyAvailable() - allocation.getQuantityTaken());
    //     allocation.setComponent(component);
    //     return allocationRepo.save(allocation);
    // }




}

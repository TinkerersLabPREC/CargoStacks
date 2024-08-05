package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Excpetions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ComponentsService {

    @Autowired
    ComponentsRepo componentsRepo;

    public Component getComponentById(int id) {
        Component component = componentsRepo.findById(id).get();
        if(component == null){
            throw new ResourceNotFoundException("Component resource with specified id not found" , id);
        }
        return component;
    }

    public List<Component> getAllComponents() {
        return componentsRepo.findAll();
    }

    public List<Component> searchComponent(String keyword) {
        return componentsRepo.searchComponent(keyword);
    }

    public Component addComponent(Component component) {
        try {
            componentsRepo.save(component);
        } catch (Exception e) {
            throw new RuntimeException("Component with similar name/model-name already exists");
        }
        return component;
    }

    public Component deleteById(int id) {
        Component component = getComponentById(id);
        componentsRepo.deleteById(id);
        return component;
    }

    public Component updateComponent(Component component) {
        return componentsRepo.save(component);
    }
}

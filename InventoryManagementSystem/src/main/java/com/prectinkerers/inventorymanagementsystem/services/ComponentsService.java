package com.prectinkerers.inventorymanagementsystem.services;

import com.prectinkerers.inventorymanagementsystem.dao.components.Component;
import com.prectinkerers.inventorymanagementsystem.repository.ComponentsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class ComponentsService {
    @Autowired
    ComponentsRepo componentsRepo;

    @Value("project.imagesPath")
    private String imagesPath;

    public Component getComponentById(int id) {
        return componentsRepo.findById(id).orElse(new Component());
    }


    public List<Component> getAllComponents() {
        return componentsRepo.findAll();
    }

    public List<Component> searchComponent(String keyword) {
        return componentsRepo.searchComponent(keyword);
    }

    public Component addComponent(Component component, MultipartFile image) throws IOException {

        Component component1 = componentsRepo.save(component);
        Path filePath = Path.of(imagesPath, String.valueOf(component1.getId()), image.getOriginalFilename());
        Files.write(filePath, image.getBytes());
        component1.setImage(String.valueOf(filePath));
        return componentsRepo.save(component1);

    }

    public Component deleteById(int id) {
        Component component = getComponentById(id);
        componentsRepo.deleteById(id);
        return component;
    }

    public Component updateComponent(Component component) {
        return componentsRepo.save(component);
    }

    public void getImagebyId(int id) {
    }
}

package com.prectinkerers.inventorymanagementsystem.controllers;

import com.prectinkerers.inventorymanagementsystem.dao.components.Component;
import com.prectinkerers.inventorymanagementsystem.services.ComponentsService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ComponentController {

    @Value("project.imagesPath")
    private String imagesPath;

    @Autowired
    private ComponentsService componentsService;

    @GetMapping("/components")
    public ResponseEntity<List<Component>> getAllComponents() {

        return new ResponseEntity<>(componentsService.getAllComponents(), HttpStatus.OK);
    }

    @GetMapping("/component/{id}")
    public ResponseEntity<Component> getComponentById(@PathVariable int id) {

        return new ResponseEntity<>(componentsService.getComponentById(id), HttpStatus.OK);
    }

    @GetMapping("/component/search")
    public ResponseEntity<List<Component>> searchComponent(@RequestParam String keyword) {

        return new ResponseEntity<>(componentsService.searchComponent(keyword) , HttpStatus.OK);
    }

    @GetMapping("/component/{id}/image")
    public ResponseEntity<byte[]> getComponentImage(@PathVariable int id) {
        return null;
    }

    @PostMapping("/component")
    public ResponseEntity<Component> addComponent(@RequestBody Component component, @RequestParam("image") MultipartFile image) {
        try {
            Component component1 = componentsService.addComponent(component, image);
            return new ResponseEntity<>(component1, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/component/{id}")
    public ResponseEntity<Component> deleteComponent(@PathVariable int id) {
        return new ResponseEntity<>(componentsService.deleteById(id), HttpStatus.OK);
    }

    @PutMapping("/component")
    public ResponseEntity<Component> updateComponent(@RequestBody Component component) {
        return new ResponseEntity<>(componentsService.updateComponent(component), HttpStatus.OK);

    }

}

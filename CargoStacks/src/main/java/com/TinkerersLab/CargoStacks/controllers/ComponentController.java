package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.services.ComponentsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ComponentController {

    @Autowired
    private ComponentsServiceImpl componentsService;

        // @GetMapping("/components")
        // public ResponseEntity<List<Component>> getAllComponents() {

        //     return new ResponseEntity<>(componentsService.getAll(), HttpStatus.OK);
        // }

        // @GetMapping("/component/{id}")
        // public ResponseEntity<Component> getComponentById(@PathVariable int id) {

        //     return new ResponseEntity<>(componentsService.getComponentById(id), HttpStatus.OK);
        // }

        // @GetMapping("/component/search")
        // public ResponseEntity<List<Component>> searchComponent(@RequestParam String keyword) {

        //     return new ResponseEntity<>(componentsService.searchComponent(keyword), HttpStatus.OK);
        // }

        // @GetMapping("/component/{id}/image")
        // public ResponseEntity<byte[]> getComponentImage(@PathVariable int id) {

        //     return null;
        // }

        // @PostMapping("/component")
        // public ResponseEntity<Component> addComponent(@RequestBody Component component) {

        //     return new ResponseEntity<>(componentsService.addComponent(component), HttpStatus.OK);
        // }

        // @DeleteMapping("/component/{id}")
        // public ResponseEntity<Component> deleteComponent(@PathVariable int id) {
        //     return new ResponseEntity<>(componentsService.deleteById(id), HttpStatus.OK);
        // }

        // @PutMapping("/component")
        // public ResponseEntity<Component> updateComponent(@RequestBody Component component) {
        //     return new ResponseEntity<>(componentsService.updateComponent(component), HttpStatus.OK);
        // }

}

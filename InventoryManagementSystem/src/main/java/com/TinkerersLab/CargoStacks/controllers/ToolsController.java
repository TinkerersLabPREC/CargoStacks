package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.services.ToolsService;
import com.TinkerersLab.CargoStacks.dao.labratoryTools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ToolsController {

    @Autowired
    private ToolsService toolsService;


    @GetMapping("/tools")
    public ResponseEntity<List<Tools>> getTools() {

        return new ResponseEntity<>(toolsService.getAllTools(), HttpStatus.OK);
    }

    @GetMapping("/tool/{id}")
    public ResponseEntity<Tools> getToolsById(@PathVariable int id) {

        return new ResponseEntity<>(toolsService.getToolById(id), HttpStatus.OK);
    }

    @GetMapping("/tools/search")
    public ResponseEntity<List<Tools>> searchTools(@RequestParam String keyword) {

        return new ResponseEntity<>(toolsService.searchTools(keyword), HttpStatus.OK);
    }

    @GetMapping("/tools/{id}/image")
    public ResponseEntity<byte[]> getToolsImage(@PathVariable int id) {
        return null;
    }

    @PostMapping("/tool")
    public ResponseEntity<Tools> addTools(@RequestBody Tools tool) {

        return new ResponseEntity<>(toolsService.addTool(tool), HttpStatus.OK);
    }

    @DeleteMapping("/tool/{id}")
    public ResponseEntity<Tools> deleteTool(@PathVariable int id) {

        return new ResponseEntity<>(toolsService.deleteById(id), HttpStatus.OK);
    }

    @PutMapping("/tool")
    public ResponseEntity<Tools> updateTool(@RequestBody Tools tool) {

        return new ResponseEntity<>(toolsService.updateTools(tool), HttpStatus.OK);
    }

}

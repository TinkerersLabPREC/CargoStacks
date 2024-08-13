package com.TinkerersLab.CargoStacks.controllers;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.services.ToolServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/tools")
@CrossOrigin
public class ToolsController {

    @Autowired
    private ToolServiceImpl toolService;


    // @GetMapping("/tools")
    // public ResponseEntity<List<Tool>> getTools() {

    //     return new ResponseEntity<>(toolService.getAllTools(), HttpStatus.OK);
    // }

    // @GetMapping("/tool/{id}")
    // public ResponseEntity<Tool> getToolsById(@PathVariable int id) {

    //     return new ResponseEntity<>(toolService.getToolById(id), HttpStatus.OK);
    // }

    // @GetMapping("/tools/search")
    // public ResponseEntity<List<Tool>> searchTools(@RequestParam String keyword) {

    //     return new ResponseEntity<>(toolService.searchTools(keyword), HttpStatus.OK);
    // }

    // @GetMapping("/tools/{id}/image")
    // public ResponseEntity<byte[]> getToolsImage(@PathVariable int id) {
    //     return null;
    // }

    // @PostMapping("/tool")
    // public ResponseEntity<Tool> addTools(@RequestBody Tool tool) {

    //     return new ResponseEntity<>(toolService.addTool(tool), HttpStatus.OK);
    // }

    // @DeleteMapping("/tool/{id}")
    // public ResponseEntity<Tool> deleteTool(@PathVariable int id) {

    //     return new ResponseEntity<>(toolService.deleteById(id), HttpStatus.OK);
    // }

    // @PutMapping("/tool")
    // public ResponseEntity<Tool> updateTool(@RequestBody Tool tool) {

    //     return new ResponseEntity<>(toolService.updateTools(tool), HttpStatus.OK);
    // }

}

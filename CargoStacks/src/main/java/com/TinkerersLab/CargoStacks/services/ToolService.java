package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import com.TinkerersLab.CargoStacks.repository.ToolRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolService {

    @Autowired
    ToolRepo toolRepo;

    public List<Tool> getAllTools() {
        return toolRepo.findAll();
    }


    public Tool getToolById(int id) {
        return toolRepo.findById(id).orElse(new Tool());
    }

    public List<Tool> searchTools(String keyword) {
        return toolRepo.searchTools(keyword);
    }

    public Tool addTool(Tool tool) {
        return toolRepo.save(tool);
    }

    public Tool deleteById(int id) {
        Tool tool = getToolById(id);
        toolRepo.delete(tool);
        return tool;
    }

    public Tool updateTools(Tool tool) {
        return toolRepo.save(tool);
    }
}

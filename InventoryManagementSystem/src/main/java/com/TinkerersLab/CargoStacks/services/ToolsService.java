package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.repository.ToolsRepo;
import com.TinkerersLab.CargoStacks.dao.labratoryTools.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolsService {
    @Autowired
    ToolsRepo toolsRepo;

    public List<Tools> getAllTools() {
        return toolsRepo.findAll();
    }


    public Tools getToolById(int id) {
        return toolsRepo.findById(id).orElse(new Tools());
    }

    public List<Tools> searchTools(String keyword) {
        return toolsRepo.searchTools(keyword);
    }

    public Tools addTool(Tools tool) {
        return toolsRepo.save(tool);
    }

    public Tools deleteById(int id) {
        Tools tool = getToolById(id);
        toolsRepo.delete(tool);
        return tool;
    }

    public Tools updateTools(Tools tool) {
        return toolsRepo.save(tool);
    }
}

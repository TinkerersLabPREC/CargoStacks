package com.TinkerersLab.CargoStacks.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.TinkerersLab.CargoStacks.models.ResourceContentType;

@Service
public class FileServiceImpl implements FileService {

    @Override
    public String saveFile(MultipartFile file, String path) throws IOException {
        Path dirPath =  Paths.get(path);
        Files.createDirectories(dirPath);
        
        Path filePath = Paths.get(path, file.getOriginalFilename());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return filePath.toString();
    }

    @Override
    public ResourceContentType getFile(String path) {

        Path filePath = Paths.get(path);
        Resource resource = new FileSystemResource(filePath);

        ResourceContentType resourceContentType = new ResourceContentType();
        resourceContentType.setResource(resource);
        return resourceContentType;
    }

}

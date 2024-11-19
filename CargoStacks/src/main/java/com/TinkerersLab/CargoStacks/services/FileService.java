package com.TinkerersLab.CargoStacks.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.TinkerersLab.CargoStacks.models.ResourceContentType;

public interface FileService {

    String saveFile(MultipartFile file, String path) throws IOException;

    ResourceContentType getFile(String path);

}

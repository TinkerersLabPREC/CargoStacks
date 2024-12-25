package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.Exceptions.ResourceNotFoundException;
import com.TinkerersLab.CargoStacks.config.ApplicationProperties;
import com.TinkerersLab.CargoStacks.dtos.ComponentDto;
import com.TinkerersLab.CargoStacks.models.CustomPageResponse;
import com.TinkerersLab.CargoStacks.models.ResourceContentType;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import com.TinkerersLab.CargoStacks.repository.ComponentsRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ComponentsServiceImpl implements ComponentService {

    ComponentsRepo componentsRepo;

    ModelMapper modelMapper;

    FileServiceImpl fileService;

    ApplicationProperties applicationProperties;

    ObjectMapper objectMapper;

    @Override
    public ComponentDto create(ComponentDto componentDto) {
        componentDto.setId(UUID.randomUUID().toString());
        componentDto.setTotalImages(0);
        Component newComponent = componentsRepo.save(dtoToEntity(componentDto));
        return entityToDto(newComponent);
    }

    @Override
    public CustomPageResponse<ComponentDto> getAll(int pageNumber, int pageSize, String sortBy, String sortSeq) {

        Sort sort;
        if (sortBy.equals("descending")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Component> componentPage = componentsRepo.findAll(pageRequest);
        List<Component> components = componentPage.getContent();

        List<ComponentDto> componentDtos = components
                .stream()
                .map(course -> entityToDto(course))
                .toList();

        CustomPageResponse<ComponentDto> customPageResponse = CustomPageResponse
                .<ComponentDto>builder()
                .pageNumber(pageNumber)
                .pageSize(pageSize)
                .totalPages(componentPage.getTotalPages())
                .totalElements(componentPage.getTotalElements())
                .isLast(componentPage.isLast())
                .content(componentDtos)
                .build();

        return customPageResponse;
    }

    @Override
    public ComponentDto getById(String componentId) {
        Component component = componentsRepo.findById(componentId).get();

        return entityToDto(component);
    }

    @Override
    public List<ComponentDto> search(String keyword) {
        List<Component> result = componentsRepo.searchComponent(keyword);
        return result
                .stream()
                .map(components -> entityToDto(components))
                .toList();
    }

    @Override
    public ComponentDto deleteById(String componentId) {
        Component component = componentsRepo
                .findById(componentId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Component with provided id not found !!", componentId));
        componentsRepo.delete(component);
        return entityToDto(component);
    }

    @Override
    public ComponentDto update(ComponentDto newComponentDto, String componentId) {
        Component oldComponent = componentsRepo
                .findById(componentId)
                .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));

        oldComponent.setCurrentlyAvailable(newComponentDto.getCurrentlyAvailable());
        oldComponent.setDescription(newComponentDto.getDescription());
        oldComponent.setName(newComponentDto.getName());
        oldComponent.setLocation(newComponentDto.getLocation());
        oldComponent.setModelName(newComponentDto.getLocation());
        oldComponent.setTotal(newComponentDto.getTotal());

        componentsRepo.save(oldComponent);
        return entityToDto(oldComponent);

    }

    @Override
    public void saveComponentImage(MultipartFile file, String componentId) {

        Component component = componentsRepo
                .findById(componentId)
                .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));

        String path = applicationProperties.getRepository() +
                File.separator + "components" +
                File.separator + componentId;

        String imagePath;
        try {
            imagePath = fileService.saveFile(file, path);
        } catch (IOException e) {
            throw new RuntimeException("Could not save image file");
        }

        if (component.getImages() == null) {
            List<com.TinkerersLab.CargoStacks.models.File> images = new ArrayList<>();
            images.add(new com.TinkerersLab.CargoStacks.models.File(file.getContentType(), imagePath));
            component.setTotalImages(1);
            try {
                component.setImages(objectMapper.writeValueAsString(images));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(
                        "Something went wrong while processing images json string from component object "
                                + e.getMessage());
            }
        } else {
            try {
                List<com.TinkerersLab.CargoStacks.models.File> images = objectMapper.readValue(component.getImages(),
                        new TypeReference<List<com.TinkerersLab.CargoStacks.models.File>>() {
                        });
                images.add(new com.TinkerersLab.CargoStacks.models.File(file.getContentType(), imagePath));
                component.setImages(objectMapper.writeValueAsString(images));
                component.setTotalImages(component.getTotalImages() + 1);
            } catch (JsonMappingException e) {
                throw new RuntimeException(
                        "Invalid images(JsonStirng) value in component object coming from db " + e.getMessage());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(
                        "Something went wrong while processing images json string from component object "
                                + e.getMessage());
            }
        }
        componentsRepo.save(component);
    }

    @Override
    public ResourceContentType getComponentImage(String componentId, int idx) {

        Component component = componentsRepo
                .findById(componentId)
                .orElseThrow(() -> new ResourceNotFoundException("Component with provided id not found", componentId));

        com.TinkerersLab.CargoStacks.models.File requestedImage;
        if (component.getImages() == null || component.getTotalImages() == 0) {
            throw new RuntimeException("Provieded Component: " + componentId + " has no image resource");
        } else {
            try {
                List<com.TinkerersLab.CargoStacks.models.File> images = objectMapper.readValue(component.getImages(),
                        new TypeReference<List<com.TinkerersLab.CargoStacks.models.File>>() {
                        });
                if (idx > images.size() || idx < 0) {
                    throw new RuntimeException("Image with provided index not found");
                }
                requestedImage = images.get(idx);

            } catch (JsonMappingException e) {
                throw new RuntimeException(
                        "Invalid images(JsonStirng) value in component object coming from db " + e.getMessage());

            } catch (JsonProcessingException e) {
                throw new RuntimeException(
                        "Something went wrong while processing images json string from component object "
                                + e.getMessage());
            }
        }
        ResourceContentType resourceContentType = fileService.getFile(requestedImage.getPath());
        resourceContentType.setContentType(requestedImage.getContentType());

        return resourceContentType;
    }

    public Component dtoToEntity(ComponentDto componentDto) {
        return modelMapper.map(componentDto, Component.class);
    }

    public ComponentDto entityToDto(Component component) {
        return modelMapper.map(component, ComponentDto.class);
    }

}

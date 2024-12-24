package com.TinkerersLab.CargoStacks.models;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Embeddable
@Data
@AllArgsConstructor
public class File {

    private String contentType;

    private String path;

}

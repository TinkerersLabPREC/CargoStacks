package com.TinkerersLab.CargoStacks.models;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class File {

    private String contentType;

    private String path;

}

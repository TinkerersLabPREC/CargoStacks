package com.TinkerersLab.CargoStacks.models.dao.components;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "component")
public class Component {

    @Id
    private String id;

    @Column(nullable = false, unique = true, length = 50)
    private String name; // actual component name

    @Column(nullable = false, length = 200)
    private String description;

    @Column(nullable = false, length = 50)
    private String modelName; // model no.

    @Column(nullable = false, length = 40)
    private String location; // to locate component in the lab

    @Column(nullable = false)
    private int total;

    @Column(nullable = false)
    private int currentlyAvailable;

    @Column(length = 500)
    private String images; // JSON object containing List<File> object in json string

    @Column(nullable = false)
    private int totalImages;

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL)
    private List<Allocation> allocations;

}

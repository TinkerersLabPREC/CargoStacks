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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String name;         //actual component name

    @Column(nullable = false)
    private String description;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String modelName;  //model no.

    @Column(nullable = false)
    private String location;         //to locate component in the lab

    private int total;

    private int currentlyAvailable;

    @OneToMany(mappedBy = "component")
    private List<Allocation> allocations;

}

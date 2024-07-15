package com.prectinkerers.inventorymanagementsystem.dao.components;

import com.prectinkerers.inventorymanagementsystem.dao.components.allocation.Allocation;
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

    private String name;             //model no.
    private String description;
    private String explanatoryName;  //actual component name
    private String location;         //to locate component in the lab
    private int total;
    private int currentlyAvailable;
    private String image;

    @OneToMany(mappedBy = "component")
    private List<Allocation> allocations;

}

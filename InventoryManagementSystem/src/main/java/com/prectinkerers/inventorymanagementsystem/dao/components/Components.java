package com.prectinkerers.inventorymanagementsystem.dao.components;

import com.prectinkerers.inventorymanagementsystem.dao.components.allocation.Allocation;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "components")
public class Components {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;             //model no.
    private String description;
    private String explanatoryName;  //actual component name
    private String location;         //to locate component in the lab
    private int total;
    private int currentlyAvailable;

    @OneToMany(mappedBy = "components")
    private List<Allocation> allocations;

}

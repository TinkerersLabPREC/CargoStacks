package com.prectinkerers.inventorymanagementsystem.dao.components.allocation;

import com.prectinkerers.inventorymanagementsystem.dao.components.Components;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "allocation")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Beneficiary beneficiary;

    private String projectName;

    @ManyToOne
    private Components components;

}

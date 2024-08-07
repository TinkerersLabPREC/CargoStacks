package com.TinkerersLab.CargoStacks.models.dao.components.allocation;

import com.TinkerersLab.CargoStacks.models.dao.components.Component;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "allocation")
public class Allocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(
            nullable = false,
            length = 500
    )
    private Beneficiary beneficiary;

    @Column(
        nullable = false
    )
    private int quantityTaken;

    private String projectName;

    @ManyToOne
    private Component component;

}

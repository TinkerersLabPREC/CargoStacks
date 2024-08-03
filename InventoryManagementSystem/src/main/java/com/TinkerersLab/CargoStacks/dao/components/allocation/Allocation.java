package com.TinkerersLab.CargoStacks.dao.components.allocation;

import com.TinkerersLab.CargoStacks.dao.components.Component;
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

    @Column(
            nullable = false,
            length = 500
    )
    private Beneficiary beneficiary;

    private String projectName;

    @ManyToOne
    private Component component;

}

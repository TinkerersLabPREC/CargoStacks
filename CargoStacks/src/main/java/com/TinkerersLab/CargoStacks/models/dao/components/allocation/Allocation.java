package com.TinkerersLab.CargoStacks.models.dao.components.allocation;

import java.util.Date;

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
    private String id;

    @Column(
            nullable = false
    )
    private Beneficiary beneficiary;

    @Column(
            nullable = false
    )
    private int quantityTaken;

    @Column(
            nullable = false,
            length = 100
    )
    private String projectName;

    @Column(
            nullable = false
    )
    private Date allocationDate;

    @Column(
            nullable = false
    )
    private boolean isReturned;

    @ManyToOne
    private Component component;

}

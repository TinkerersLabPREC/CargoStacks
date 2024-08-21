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
    
    private boolean isReturned = false;
    
    @ManyToOne
    private Component component;

}

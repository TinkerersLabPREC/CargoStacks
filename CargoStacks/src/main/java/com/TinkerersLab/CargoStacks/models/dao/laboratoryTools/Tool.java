package com.TinkerersLab.CargoStacks.models.dao.laboratoryTools;

import com.TinkerersLab.CargoStacks.models.File;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tools")
public class Tool {

    @Id
    private String id;

    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String name;

    @Column(
            nullable = false,
            unique = true,
            length = 50
    )
    private String modelName;

    @Column(
            nullable = false,
            length = 500
    )
    private String description;

    @Column(
            nullable = false
    )
    private int price;

    @Column(
            length = 100
    )
    private String requiredSoftware;

    private File image;

    @OneToMany(mappedBy = "tool", cascade = CascadeType.ALL)
    private List<Utilization> utilizations;

}

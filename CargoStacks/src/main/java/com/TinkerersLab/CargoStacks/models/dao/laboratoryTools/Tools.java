package com.TinkerersLab.CargoStacks.models.dao.laboratoryTools;

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
public class Tools {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(
            nullable = false,
            unique = true,
            length = 200
    )
    private String name;

    @Column(
            nullable = false,
            unique = true,
            length = 200
    )
    private String modelName;
    private String description;
    private int price;
    private String requiredSoftware;

    @OneToMany(mappedBy = "tool")
    private List<Utilization> utilization;
}

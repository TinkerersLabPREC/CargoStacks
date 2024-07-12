package com.prectinkerers.inventorymanagementsystem.dao.labratoryTools;

import com.prectinkerers.inventorymanagementsystem.dao.labratoryTools.utilization.Utilization;
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

    private String name;
    private String modelName;
    private String description;
    private int price;
    private String requiredSoftware;

    @OneToMany(mappedBy = "tool")
    private List<Utilization> utilization;
}

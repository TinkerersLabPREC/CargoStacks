package com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import java.util.Date;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilization")
public class Utilization {

    @Id
    private String id;

    @Column(nullable = false)
    private Utilizer utilizer;

    private Date UtilizationTime;

    private int estimatedTimeRequired;          //estimation tool utilization time in hours

    @ManyToOne
    private Tool tool;

}

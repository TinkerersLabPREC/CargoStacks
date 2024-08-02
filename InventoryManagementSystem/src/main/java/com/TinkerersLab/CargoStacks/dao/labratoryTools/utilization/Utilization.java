package com.TinkerersLab.CargoStacks.dao.labratoryTools.utilization;

import com.TinkerersLab.CargoStacks.dao.labratoryTools.Tools;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

@Data
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "utilization")
public class Utilization {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private Utilizer utilizer;
    private Time UtilizationTime;
    private int estimatedTimeRequired;          //estimation tool utilization time in hours

    
    @ManyToOne
    @Column(nullable = false)
    private Tools tool;

}

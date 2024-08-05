package com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tools;
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
    private Tools tool;

}

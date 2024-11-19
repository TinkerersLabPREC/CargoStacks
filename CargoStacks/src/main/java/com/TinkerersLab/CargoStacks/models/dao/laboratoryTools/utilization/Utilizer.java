package com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilizer {

    @Column(
            nullable = false,
            length = 50
    )
    private String name;

    @Column(
            nullable = false
    )
    private String email;

    @Column(
            nullable = false,
            length = 12
    )
    private String phone;

    @Column(
            nullable = false
    )
    private String department;


    @Column(
            nullable = false
    )
    private String year;

    @Column(
            nullable = false,
            length = 150
    )
    private String address;


    @Column(
            nullable = false,
            length = 150
    )
    private String usageDescription;


}

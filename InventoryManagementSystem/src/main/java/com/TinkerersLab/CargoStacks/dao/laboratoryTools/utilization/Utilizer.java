package com.TinkerersLab.CargoStacks.dao.laboratoryTools.utilization;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilizer {

    private String name;
    private String email;
    private String phone;
    private String department;
    private String year;
    private String address;
    private String usageDescription;


}

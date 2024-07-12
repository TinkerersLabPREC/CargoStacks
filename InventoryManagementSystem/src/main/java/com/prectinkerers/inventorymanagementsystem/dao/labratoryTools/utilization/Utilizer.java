package com.prectinkerers.inventorymanagementsystem.dao.labratoryTools.utilization;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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

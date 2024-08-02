package com.TinkerersLab.CargoStacks.dao.components.allocation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Embeddable
public class Beneficiary {
    
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date returnDate;
    private String department;
    private String year;


}

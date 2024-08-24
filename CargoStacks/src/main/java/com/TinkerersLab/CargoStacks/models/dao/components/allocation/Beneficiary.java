package com.TinkerersLab.CargoStacks.models.dao.components.allocation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
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
    
    @Column(
        nullable = false,
        length = 50
    )
    private String name;

    @Column(
        nullable = false,
        length = 35
    )
    @Email
    private String email;

    @Column(
        nullable = false,
        length = 12
    )
    private String phone;

    @Column(
        nullable = false,
        length = 100
    )
    private String address;

    @Column(
        nullable = false
    )
    private Date returnDate;

    @Column(
        nullable = false
    )
    private String department;

    @Column(
        nullable = false
    )
    private String year;
    
}

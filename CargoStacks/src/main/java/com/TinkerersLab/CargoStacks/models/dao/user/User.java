package com.TinkerersLab.CargoStacks.models.dao.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "users")
@Entity
public class User {

    @Id
    private String id;

    @Column(
        unique = true, 
        nullable = false,
        length = 35
    )
    private String username;

    @Column(
        unique = true, 
        nullable = false,
        length = 50
    )
    private String password;

    @Column(
        length = 50
    )
    private String userDescription;

}

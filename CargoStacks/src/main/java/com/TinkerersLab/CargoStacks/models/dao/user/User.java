package com.TinkerersLab.CargoStacks.models.dao.user;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "app_user")
@Entity
public class User {

    @Id
    @Column(
        length = 12
    )
    private String id;

    @Column(
        unique = true, 
        nullable = false,
        length = 50
    )
    private String email;

    @Column(
        unique = true, 
        nullable = false,
        length = 150
    )
    private String password;

    @Column(
        length = 150
    )
    private String userDescription;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public void assignRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}

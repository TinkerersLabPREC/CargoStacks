package com.TinkerersLab.CargoStacks.models.dao.user;

import java.util.ArrayList;
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
    private String id;

    @Column(unique = true, nullable = false, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String password;

    @Column(length = 150)
    private String userDescription;

    @Column(nullable = false, length = 50)
    private String Organization; // college name

    @Column(nullable = false, length = 15)
    private String contact;

    @Column(nullable = false, length = 150)
    private String address;

    @Column(nullable = false, length = 30)
    private String department;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    public void assignRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
        if (role.getUsers() == null) {
            role.setUsers(new ArrayList<>());
        }
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}

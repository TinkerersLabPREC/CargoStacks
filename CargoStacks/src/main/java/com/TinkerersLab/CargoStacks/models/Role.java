package com.TinkerersLab.CargoStacks.models;

import java.util.List;

import com.TinkerersLab.CargoStacks.models.dao.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @Column(name = "role_id")
    private String id;

    @Column(name = "role_name", unique = true, nullable = false, length = 35)
    private String name;

    @Column(name = "role_description", length = 100)
    private String description;
    
    @JsonIgnore
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<User> users;
}

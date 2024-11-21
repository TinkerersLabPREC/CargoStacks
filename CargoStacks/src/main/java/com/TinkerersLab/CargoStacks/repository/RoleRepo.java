package com.TinkerersLab.CargoStacks.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.TinkerersLab.CargoStacks.models.Role;

public interface RoleRepo extends JpaRepository<Role, String> {

    Optional<Role> findByName(String name);
}
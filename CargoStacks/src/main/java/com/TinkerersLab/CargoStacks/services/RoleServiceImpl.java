package com.TinkerersLab.CargoStacks.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.TinkerersLab.CargoStacks.models.Role;
import com.TinkerersLab.CargoStacks.repository.RoleRepo;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

    RoleRepo roleRepo;

    @Override
    public Role getRole(String name) {
        return roleRepo.findByName(name).orElseThrow(() -> new UnsupportedOperationException("Role not found"));
    }

    @Override
    public Role createRole(String roleName) {
        Role role = new Role();
        role.setId(UUID.randomUUID().toString());
        role.setName(roleName);

        return roleRepo.save(role);
    }
}

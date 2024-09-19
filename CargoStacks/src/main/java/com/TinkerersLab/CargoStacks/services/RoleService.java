package com.TinkerersLab.CargoStacks.services;

import com.TinkerersLab.CargoStacks.models.Role;

public interface RoleService {

    Role getRole(String name);

    Role createRole(String roleName);


}

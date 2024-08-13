package com.TinkerersLab.CargoStacks.repository;

import com.TinkerersLab.CargoStacks.models.dao.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String> {


    User findByUsername(String username);
}

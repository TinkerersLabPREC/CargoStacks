package com.TinkerersLab.CargoStacks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TinkerersLab.CargoStacks.dao.user.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    
    User findByUsername(String username);
}

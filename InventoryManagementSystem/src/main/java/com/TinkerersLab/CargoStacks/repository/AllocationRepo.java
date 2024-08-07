package com.TinkerersLab.CargoStacks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;

@Repository
public interface AllocationRepo extends JpaRepository<Allocation, Integer> {

}

package com.TinkerersLab.CargoStacks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TinkerersLab.CargoStacks.models.dao.components.allocation.Allocation;
import com.TinkerersLab.CargoStacks.models.dao.components.Component;


@Repository
public interface AllocationRepo extends JpaRepository<Allocation, String> {

    Page<Allocation> findByComponent(Component component, PageRequest pageRequest);
}

package com.TinkerersLab.CargoStacks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;

@Repository
public interface UtilizationRepo extends JpaRepository<Utilization, String> {

}

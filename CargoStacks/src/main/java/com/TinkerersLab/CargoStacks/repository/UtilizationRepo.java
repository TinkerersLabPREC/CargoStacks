package com.TinkerersLab.CargoStacks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.utilization.Utilization;
import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;


@Repository
public interface UtilizationRepo extends JpaRepository<Utilization, String> {

    Page<Utilization> findByTool(Tool tool, PageRequest pageRequest);
}

package com.TinkerersLab.CargoStacks.repository;

import com.TinkerersLab.CargoStacks.dao.laboratoryTools.Tools;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsRepo extends JpaRepository<Tools, Integer> {

    @Query(
            "SELECT t FROM Tools t WHERE " +
                    "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(t.modelName) LIKE LOWER(CONCAT('%', :keyword , '%')) "
    )
    List<Tools> searchTools(String keyword);
}

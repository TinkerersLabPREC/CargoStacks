package com.TinkerersLab.CargoStacks.repository;

import com.TinkerersLab.CargoStacks.models.dao.laboratoryTools.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepo extends JpaRepository<Tool, String> {

    @Query(
            "SELECT t FROM Tool t WHERE " +
                    "LOWER(t.name) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(t.description) LIKE LOWER(CONCAT('%', :keyword , '%')) OR " +
                    "LOWER(t.modelName) LIKE LOWER(CONCAT('%', :keyword , '%')) "
    )
    List<Tool> searchTools(String keyword);
}

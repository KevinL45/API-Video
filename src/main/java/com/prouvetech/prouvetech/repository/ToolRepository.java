package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.Tool;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    Tool findByName(String name);

}

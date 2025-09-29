package com.prouvetech.prouvetech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.prouvetech.prouvetech.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String name);
}

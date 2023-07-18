package com.example.Prog4.repository;

import com.example.Prog4.model.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeRepository extends JpaRepository<EmployeEntity, Long> {
}

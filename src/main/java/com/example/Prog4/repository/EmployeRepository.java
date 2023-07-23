package com.example.Prog4.repository;

import com.example.Prog4.model.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface EmployeRepository extends JpaRepository<EmployeEntity, Long> {
    @Query(value = "SELECT * FROM employee WHERE " +
            "(starting_date BETWEEN :start AND :end) OR " +
            "(closing_date IS NOT NULL AND closing_date BETWEEN :start AND :end)",
            nativeQuery = true)
    List<EmployeEntity> filterEmployeByDate(LocalDate start, LocalDate end);

    @Query(value = "SELECT * FROM employee " +
            "WHERE UPPER(name) LIKE %:keyword% " +
            "OR UPPER(first_name) LIKE %:keyword% " +
            "OR UPPER(sexe) LIKE %:keyword% " +
            "OR UPPER(function) LIKE %:keyword%",
            nativeQuery = true)
    List<EmployeEntity> filterEmployeByNameOrFirstNameOrFunctionOrSexe(String keyword);

    @Query(value = "SELECT * FROM employee e ORDER BY e.starting_date ASC, e.closing_date ASC , e.name ASC ,e.first_name ASC , e.function ASC" , nativeQuery = true)
    List<EmployeEntity> findAllEmployeesSortedAsc();

    @Query(value = "SELECT * FROM employee e ORDER BY e.starting_date DESC, e.closing_date DESC, e.name DESC ,e.first_name DESC , e.function DESC", nativeQuery = true)
    List<EmployeEntity> findAllEmployeesSortedDesc();
}

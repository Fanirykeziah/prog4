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

    @Query(value = "select phone_numbers from employe_phone_numbers where phone_numbers in :phoneNumbers union select phone_numbers from company_phone_numbers where phone_numbers in :phoneNumbers", nativeQuery = true)
    String findByPhoneNumber(List<String> phoneNumbers);

    @Query(value = "SELECT employee" +
            " FROM employee JOIN employe_phone_numbers ON employee.id = employe_phone_numbers.employe_id WHERE SUBSTRING(employe_phone_numbers.phone_numbers, 1, 3) = ':phoneNumber'",nativeQuery = true)
    List<EmployeEntity> filterByPhoneNumber(String phoneNumber);
}

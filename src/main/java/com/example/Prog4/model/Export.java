package com.example.Prog4.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "export")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Export {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    @Column(name = "img")
    private String img;

    @Column(name = "matricule" , unique = true)
    private String matricule;

    @Column(name = "name")
    private String name;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "age")
    private int age;

    @Column(name = "starting_date")
    private LocalDate startingDate;

    @Column(name = "ending_date")
    private LocalDate endingDate;

    @Column(name = "cnaps_number")
    private String cnapsNumber;

    @Column(name = "salary")
    private String salary;

    @Column(name = "logo")
    private String logo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "nif")
    private String nif;

    @Column(name = "stat")
    private String stat;

    @Column(name = "company_address")
    private String companyAddress;

    @Column(name = "company_number")
    private List<String> companyNumber;

    @Column(name = "email")
    private String email;
}

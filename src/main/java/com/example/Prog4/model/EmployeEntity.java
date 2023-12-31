package com.example.Prog4.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class EmployeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    private String name;

    private String first_name;

    @Enumerated(value = EnumType.STRING)
    private Gender sexe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cin_id")
    private CinEntity cin;

    private String function;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate starting_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closing_date;

    @Column(unique = true)
    private String matricule;

    private String photos;

    private Integer children_number;

    private String email_perso;

    private String email_pro;

    private List<String> numeros;

    private String location;

    @Enumerated(value = EnumType.STRING)
    private CspType csp;

    private String cnaps;
}
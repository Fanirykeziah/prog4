package com.example.Prog4.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Table(name = "cin")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CinEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_seq")
    @SequenceGenerator(name = "employee_seq", sequenceName = "employee_seq", allocationSize = 1)
    private Long id;

    private String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate issue_date;

    private String issue_location;
}

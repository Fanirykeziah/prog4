package com.example.Prog4.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employe {
    private Long id;

    private String name;

    private String first_name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private CinEntity cin;

    private MultipartFile photos;

    @Enumerated(value = EnumType.STRING)
    private Gender sexe;

    private String function;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate starting_date;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closing_date;

    @Column(unique = true)
    private String matricule;

    private Integer children_number;

    private String email_perso;

    private String email_pro;

    private List<String> numeros;

    private String location;

    private CspType csp;

    private String cnaps;
}

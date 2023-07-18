package com.example.Prog4.model;

import jakarta.persistence.Column;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Date;

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
    private MultipartFile photos;
}

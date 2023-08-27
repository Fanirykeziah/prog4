package com.example.Prog4.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "company_conf")
@Entity
@Builder
public class Company {
    @Id
    private Long id;
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    @ElementCollection
    @CollectionTable(name = "company_phone_numbers", joinColumns = @JoinColumn(name = "company_id"))
    @Column(name = "phone_numbers", unique = true)
    private List<String> phoneNumbers = new ArrayList<>();
    private String nif;
    private String stat;
    private String logo;
}

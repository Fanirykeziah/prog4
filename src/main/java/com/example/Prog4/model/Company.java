package com.example.Prog4.model;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class Company {
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    private List<String> phone_number;
    private String fiscale_id;
    private MultipartFile logo;
}

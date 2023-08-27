package com.example.Prog4.service;

import com.example.Prog4.model.Company;
import com.example.Prog4.repository.CompanyRepository;
import com.example.Prog4.repository.ExportPdf;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompanyService {
    private CompanyRepository repository;

    public Company getCompany(Long id){
        return repository.findById(id).orElse(null);
    }
}

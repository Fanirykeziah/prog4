package com.example.Prog4.repository;

import com.example.Prog4.model.Export;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExportPdf extends JpaRepository<Export , Long> {
}

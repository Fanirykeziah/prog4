package com.example.Prog4.service;

import com.example.Prog4.model.Company;
import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;
import com.example.Prog4.model.Export;
import com.example.Prog4.repository.ExportPdf;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.OutputStream;
import java.time.LocalDate;
import java.time.Period;

@Service
@AllArgsConstructor
public class GeneratePdfFile {
    private final TemplateEngine templateEngine;

    private ExportPdf repo;

    private final JdbcTemplate jdbcTemplate;

    public int calculateAge(EmployeEntity person) {
        LocalDate birthDate = person.getBirthday();
        LocalDate currentDate = LocalDate.now();

        Period period = Period.between(birthDate, currentDate);
        int age = period.getYears();

        return age;
    }

    public Export addEmployeeToExport(Employe employe , Company company){
        EmployeEntity employeEntity = new EmployeEntity();
        EmployeEntity emp = employeEntity.builder()
                .name(employe.getName())
                .first_name(employe.getFirst_name())
                .sexe(employe.getSexe())
                .birthday(employe.getBirthday())
                .location(employe.getLocation())
                .cin(employe.getCin())
                .function(employe.getFunction())
                .photos(employe.getPhotos().getOriginalFilename())
                .children_number(employe.getChildren_number())
                .email_perso(employe.getEmail_perso())
                .email_pro(employe.getEmail_pro())
                .phone_numbers(employe.getPhone_numbers())
                .starting_date(LocalDate.now())
                .csp(employe.getCsp())
                .cnaps(employe.getCnaps())
                .salary(employe.getSalary())
                .build();

        Export emp1 = new Export();
        Export toExport = emp1.builder()
                .img(emp.getPhotos())
                .name(emp.getName())
                .age(calculateAge(emp))
                .firstName(emp.getFirst_name())
                .startingDate(LocalDate.now())
                .endingDate(emp.getClosing_date())
                .cnapsNumber(emp.getCnaps())
                .salary(emp.getSalary())
                .logo(company.getLogo())
                .companyAddress(company.getAddress())
                .companyNumber(company.getPhoneNumbers())
                .companyName(company.getName())
                .nif(company.getNif())
                .stat(company.getStat())
                .email(company.getEmail())
                .build();

        String matricule = generateMatricule();
        toExport.setMatricule(matricule);

        return repo.save(toExport);
    }

    public Export getEmployeeToExportById(Long id){
        Export test = repo.findById(id).orElse(null);
        return test;
    }

    public void generatePdf(String templateName, EmployeEntity employee,Company company,OutputStream outputStream) throws Exception {
        Context context = new Context();
        context.setVariable("employee", employee);
        context.setVariable("company",company);

        String htmlContent = templateEngine.process(templateName, context);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        renderer.createPDF(outputStream);
    }

    public String generateMatricule() {
        String sql = "SELECT 'EMP' || LPAD(CAST(COUNT(*) + 1 AS VARCHAR), 3, '0') FROM employee";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}

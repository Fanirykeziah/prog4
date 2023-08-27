package com.example.Prog4.service;

import com.example.Prog4.model.Company;
import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;
import com.example.Prog4.repository.EmployeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private EmployeRepository repository;
    private final JdbcTemplate jdbcTemplate;

    public List<EmployeEntity> getEmploye(){
        return repository.findAll();
    }

    public EmployeEntity addEmploye(Employe employe){
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

                String fileName = StringUtils.cleanPath(emp.getPhotos());
                emp.setPhotos(fileName);
                String matricule = generateMatricule();
                emp.setMatricule(matricule);
                emp.setPhone_numbers(employe.getPhone_numbers());
                return repository.save(emp);
    }

    public EmployeEntity updateEmploye(Employe employe){
        EmployeEntity employeEntity = new EmployeEntity();
        EmployeEntity emp = employeEntity.builder()
                .id(employe.getId())
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
                .starting_date(employe.getStarting_date())
                .closing_date(employe.getClosing_date())
                .csp(employe.getCsp())
                .cnaps(employe.getCnaps())
                .matricule(employe.getMatricule())
                .build();
        String fileName = StringUtils.cleanPath(emp.getPhotos());
        emp.setPhotos(fileName);

        return repository.save(emp);
    }

    public List<EmployeEntity> getEmployeFilter(LocalDate start , LocalDate end , String keyword){

        if (start != null && end != null) {
            return repository.filterEmployeByDate(start,end);
        }
        if(keyword != null){
            return repository.filterEmployeByNameOrFirstNameOrFunctionOrSexe(keyword.toUpperCase());
        }
            return repository.findAllEmployeesSortedAsc();
    }

    public EmployeEntity getEmployeById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String generateMatricule() {
        String sql = "SELECT 'EMP' || LPAD(CAST(COUNT(*) + 1 AS VARCHAR), 3, '0') FROM employee";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}

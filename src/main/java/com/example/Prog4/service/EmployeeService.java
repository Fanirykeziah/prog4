package com.example.Prog4.service;

import com.example.Prog4.model.CinEntity;
import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;
import com.example.Prog4.repository.EmployeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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
                    .numeros(employe.getNumeros())
                    .starting_date(LocalDate.now())
                    .csp(employe.getCsp())
                    .cnaps(employe.getCnaps())
                    .build();

            String fileName = StringUtils.cleanPath(emp.getPhotos());
            emp.setPhotos(fileName);
            String matricule = generateMatricule();
            emp.setMatricule(matricule);
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
                .numeros(employe.getNumeros())
                .starting_date(employe.getStarting_date())
                .closing_date(employe.getClosing_date())
                .csp(employe.getCsp())
                .cnaps(employe.getCnaps())
                .matricule(employe.getMatricule())
                .build();

        return repository.save(emp);
    }

    public List<EmployeEntity> getEmployeFilter(LocalDate start , LocalDate end , String keyword){
        List<EmployeEntity> employe;

        if (start != null && end != null) {
            employe = repository.filterEmployeByDate(start,end);
        }
        else if(keyword != null){
            employe = repository.filterEmployeByNameOrFirstNameOrFunctionOrSexe(keyword.toUpperCase());
        }
        else {
            employe = repository.findAllEmployeesSortedAsc();
        }
        return employe;
    }

    public EmployeEntity getEmployeById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String generateMatricule() {
        String sql = "SELECT 'EMP' || LPAD(CAST(COUNT(*) + 1 AS VARCHAR), 3, '0') FROM employee";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}

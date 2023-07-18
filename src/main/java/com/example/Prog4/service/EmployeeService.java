package com.example.Prog4.service;

import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;
import com.example.Prog4.repository.EmployeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;

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
                .birthday(employe.getBirthday())
                .photos(employe.getPhotos().getOriginalFilename())
                .build();

        String fileName = StringUtils.cleanPath(emp.getPhotos());
        emp.setPhotos(fileName);
        String matricule = generateMatricule();
        emp.setMatricule(matricule);
        return repository.save(emp);
    }

    public EmployeEntity getEmployeById(Long id){
        return repository.findById(id).orElse(null);
    }

    public String generateMatricule() {
        String sql = "SELECT 'EMP' || LPAD(CAST(COUNT(*) + 1 AS VARCHAR), 3, '0') FROM employee";
        return jdbcTemplate.queryForObject(sql, String.class);
    }
}

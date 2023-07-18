package com.example.Prog4.controller;
import com.example.Prog4.model.Employe;
import com.example.Prog4.model.EmployeEntity;
import com.example.Prog4.service.EmployeeService;
import com.example.Prog4.utils.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class EmployeController {
    private EmployeeService service;

    @GetMapping("/employees")
    public String showEmployees(Model model) {
        List<EmployeEntity> employees = service.getEmploye();
        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employees/add")
    public String showForm(Model model) {
        Employe employee = new Employe();
        model.addAttribute("employee", employee);
        return "create";
    }

    @PostMapping("/employees/add")
    public String addEmployee(Employe employe) throws IOException {
        EmployeEntity savedEmployee = service.addEmploye(employe);
        String fileName = StringUtils.cleanPath(savedEmployee.getPhotos());
        String uploadDir = "src/main/resources/static/user-photos/" + savedEmployee.getId();
        FileUploadUtil.SaveFile(uploadDir, fileName, employe.getPhotos());

        return "redirect:/employees";
    }

    @GetMapping("/employee/{id}")
    public String getEmployeInformation(@PathVariable Long id, Model model) {
        EmployeEntity employee = service.getEmployeById(id);
        model.addAttribute("employee", employee);
        return "employee";
    }

    @GetMapping("/employee/{id}/edit")
    public String EditForm(Model model) {
        Employe employee = new Employe();
        model.addAttribute("employee", employee);
        return "edit";
    }
}


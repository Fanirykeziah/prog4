package com.example.Prog4.controller;
import com.example.Prog4.model.*;
import com.example.Prog4.repository.EmployeRepository;
import com.example.Prog4.service.EmployeeService;
import com.example.Prog4.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.opencsv.CSVWriter;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeController {
    private EmployeeService service;

    private EmployeRepository repository;
    @GetMapping("/employees")
    public String showEmployeesDefaultAsc(HttpSession session, Model model, @RequestParam(value = "start", required = false) LocalDate start ,
                                          @RequestParam(value = "end", required = false)LocalDate end,
                                          @RequestParam(value = "keyword", required = false)String keyword) {
       // UserEntity connectedUser = (UserEntity) session.getAttribute("sessionId");
      //  if(connectedUser != null){
                List<EmployeEntity> employees = service.getEmployeFilter(start, end, keyword);
                model.addAttribute("employees", employees);
                return "index";
      //  }
      //  return "redirect:/login";
    }

    @GetMapping("/employees/desc")
    public String showEmployeesDesc(Model model) {
        List<EmployeEntity> employees = repository.findAllEmployeesSortedDesc();
        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employees/add")
    public String showForm(Model model,HttpSession session) {
     /*   UserEntity connectedUser = (UserEntity) session.getAttribute("sessionId");
        if(connectedUser != null){
            if(connectedUser.getRole().equals("ADMIN")){*/
                Employe employee = new Employe();
                employee.setCin(new CinEntity());
                model.addAttribute("employee", employee);
                return "create";
      /*      }
            return "unauthorized";
        }
        return "redirect:/login"; */
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
    public String getEmployeInformation(@PathVariable Long id, Model model, HttpSession session) {
        UserEntity connectedUser = (UserEntity) session.getAttribute("sessionId");
        /*if(connectedUser != null){
            if(connectedUser.getRole().equals("ADMIN")){*/
                EmployeEntity employee = service.getEmployeById(id);
                model.addAttribute("employee", employee);
                return "employee";
        /*    }
            return "unauthorized";
        }


        return "redirect:/login";

         */
    }

    @GetMapping("/update/{id}")
    public String EditForm(@PathVariable Long id,Model model,HttpSession session) {
      /*  UserEntity connectedUser = (UserEntity) session.getAttribute("sessionId");
        if(connectedUser != null){
            if(connectedUser.getRole().equals("ADMIN")){ */
                EmployeEntity employee = service.getEmployeById(id);
                model.addAttribute("employee", employee);
                return "edit";
            }
         /*   return "unauthorized";
        }
        return "redirect:/login";
    }
*/
    @PostMapping("/update/{id}")
    public String updateEmploye(@PathVariable("id")Long id, @ModelAttribute Employe employe){
        service.updateEmploye(employe);
        return  "redirect:/employees";
    }

    @GetMapping("/download-employees-csv")
    public void downloadEmployeesCSV(HttpServletResponse response,@RequestParam(value = "start", required = false) LocalDate start ,
                                     @RequestParam(value = "end", required = false)LocalDate end,
                                     @RequestParam(value = "keyword", required = false)String keyword) throws IOException {
        List<EmployeEntity> employees = service.getEmployeFilter(start, end, keyword);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");

        try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
            String[] header = {"ID", "Nom", "Prénoms", "Date de naissance","Fonction","Numero cnaps","Email perso","Email pro",
            "Nombre d'enfant a sa charge","Numero de CIN","Lieu de délivrance du CIN","Date de délivrance du CIN", "Matricule de l'employe",
            "Date d'embauche","Date de depart","Sexe","Telephone"};
            csvWriter.writeNext(header);

            for (EmployeEntity employee : employees) {
                String[] data = {
                        String.valueOf(employee.getId()),
                        employee.getName(),
                        employee.getFirst_name(),
                        String.valueOf(employee.getBirthday()),
                        employee.getFunction(),
                        employee.getCnaps(),
                        employee.getEmail_perso(),
                        employee.getEmail_pro(),
                        String.valueOf(employee.getChildren_number()),
                        employee.getCin().getNumber(),
                        employee.getCin().getIssue_location(),
                        String.valueOf(employee.getCin().getIssue_date()),
                        employee.getMatricule(),
                        String.valueOf(employee.getStarting_date()),
                        String.valueOf(employee.getClosing_date()),
                        String.valueOf(employee.getSexe()) ,
                        String.valueOf(employee.getNumeros())
                };
                csvWriter.writeNext(data);
            }
        }
    }
}

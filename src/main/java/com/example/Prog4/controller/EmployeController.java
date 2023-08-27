package com.example.Prog4.controller;
import com.example.Prog4.model.*;
import com.example.Prog4.repository.EmployeRepository;
import com.example.Prog4.service.CompanyService;
import com.example.Prog4.service.EmployeeService;
import com.example.Prog4.service.GeneratePdfFile;
import com.example.Prog4.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import com.opencsv.CSVWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
public class EmployeController {
    private EmployeeService service;

    private GeneratePdfFile pdfFile;

    private CompanyService companyService;

    private EmployeRepository repository;
    @GetMapping("/employees")
    public String showEmployeesDefaultAsc(HttpSession session, Model model, @RequestParam(value = "start", required = false) LocalDate start ,
                                          @RequestParam(value = "end", required = false)LocalDate end,
                                          @RequestParam(value = "keyword", required = false)String keyword) {
         if(AuthUtils.isAuth(session)){
                List<EmployeEntity> employees = service.getEmployeFilter(start, end, keyword);
                model.addAttribute("employees", employees);
                return "index";
         }
          return "redirect:/login";
    }

    @GetMapping("/details")
    public String showDetails(@RequestParam Long employeeId, Model model, HttpSession session) {
        if(AuthUtils.isAuth(session)){
            if(AuthUtils.isAuthRole(session,"ADMIN")){
                EmployeEntity employee= service.getEmployeById(employeeId);
                Company company = companyService.getCompany(1L);
                model.addAttribute("company", company);
                model.addAttribute("employee", employee);
                return "company";
            }
            return "unauthorized";
        }
        return "redirect:/login";
    }

    @GetMapping("/employees/desc")
    public String showEmployeesDesc(Model model) {
        List<EmployeEntity> employees = repository.findAllEmployeesSortedDesc();
        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employees/country")
    public String showEmployeesDesc(Model model, @RequestParam(value = "code" , required = false) String code) {
        List<EmployeEntity> employees = repository.filterByPhoneNumber(code);
        model.addAttribute("employees", employees);
        return "index";
    }

    @GetMapping("/employees/add")
    public String showForm(Model model, HttpSession session) {
        if(AuthUtils.isAuth(session)){
            if (AuthUtils.isAuthRole(session , "ADMIN")) {
                Employe employee = new Employe();
                employee.setCin(new CinEntity());
                model.addAttribute("employee", employee);
                return "create";
            }
            return "unauthorized";
        }
        return "redirect:/login";
    }

    @PostMapping("/employees/add")
    public String addEmployee(Employe employe) throws IOException {
        String phone_number_unique = repository.findByPhoneNumber(employe.getPhone_numbers());
        for(int i = 0; i < employe.getPhone_numbers().size(); i++){
            int length = employe.getPhone_numbers().get(i).length();
            if(phone_number_unique == null & length == 13){
                EmployeEntity savedEmployee = service.addEmploye(employe);
                Company company = companyService.getCompany(1L);
                pdfFile.addEmployeeToExport(employe , company);
                String fileName = StringUtils.cleanPath(savedEmployee.getPhotos());
                String uploadDir = "src/main/resources/static/user-photos/" + savedEmployee.getId();
                FileUploadUtil.SaveFile(uploadDir, fileName, employe.getPhotos());

                return "redirect:/employees";
            }
        }
        return "IllegalArgument";
    }

    @GetMapping("/employee/{id}")
    public String getEmployeInformation(@PathVariable Long id, Model model, HttpSession session) {
        if(AuthUtils.isAuth(session)){
            if(AuthUtils.isAuthRole(session,"ADMIN")){
                EmployeEntity employee= service.getEmployeById(id);
                model.addAttribute("employee", employee);
                return "employee";
            }
            return "unauthorized";
        }
        return "redirect:/login";
    }

    @GetMapping("/update/{id}")
    public String EditForm(@PathVariable Long id,Model model,HttpSession session) {
        if (AuthUtils.isAuth(session)){
            if(AuthUtils.isAuthRole(session,"ADMIN")) {
                EmployeEntity employee = service.getEmployeById(id);
                model.addAttribute("employee", employee);
                return "edit";
            }
            return "unauthorized";
        }
        return "redirect:/login";
    }

    @PostMapping("/update/{id}")
    public String updateEmploye(@PathVariable("id")Long id, @ModelAttribute Employe employe) throws IOException {
        String phone_number_unique = repository.findByPhoneNumber(employe.getPhone_numbers());
        if(phone_number_unique == null) {
            EmployeEntity employee = service.updateEmploye(employe);
            String fileName = StringUtils.cleanPath(employee.getPhotos());
            String uploadDir = "src/main/resources/static/user-photos/" + employee.getId();
            FileUploadUtil.SaveFile(uploadDir, fileName, employe.getPhotos());
            return "redirect:/employees";
        }
        return "IllegalArgument";
    }

    @GetMapping("/generate-pdf")
    public void generatePdf(@RequestParam Long employeeId, HttpServletResponse response) throws Exception {
        EmployeEntity employee = service.getEmployeById(employeeId);
        Company company = companyService.getCompany(1L);
        String templateName = "company";

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"employee-info.pdf\"");

        pdfFile.generatePdf(templateName, employee,company, response.getOutputStream());
    }

    @GetMapping("/download-employees-csv")
    public void downloadEmployeesCSV(HttpServletResponse response,@RequestParam(value = "start", required = false) LocalDate start ,
                                     @RequestParam(value = "end", required = false)LocalDate end,
                                     @RequestParam(value = "keyword", required = false)String keyword ,
                                     HttpSession session) throws IOException {
        if(AuthUtils.isAuth(session)){
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
                            String.valueOf(employee.getPhone_numbers())
                    };
                    csvWriter.writeNext(data);
                }
            }
        }

    }
}

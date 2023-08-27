package com.example.Prog4.controller;

import com.example.Prog4.model.UserEntity;
import com.example.Prog4.repository.UserRepository;
import com.example.Prog4.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@AllArgsConstructor
public class UserController {
    private UserService service;

    private UserRepository repository;

    @GetMapping("/")
    public String homePage(HttpSession session){
        if(AuthUtils.isAuth(session)){
            return "home";
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm(Model model , UserEntity user) {
        model.addAttribute("user",user);
        return "login";
    }

    @PostMapping("/login")
    public String checkUser(@ModelAttribute("user") UserEntity user, Model model, HttpSession session){
        UserEntity userCheck = repository.findByUsername(user.getUsername());
        if(userCheck != null && userCheck.getPassword().equals(user.getPassword())){
            session.setAttribute("LoggedInEmployee", userCheck);
            return "redirect:/";
        }
        return "IllegalArgument";
    }

    @GetMapping("/signup")
    public String showSignUpForm(Model model){
        model.addAttribute("user",new UserEntity());
        return "sign_up";
    }

    @PostMapping("/signup")
    public String CreateUser(@ModelAttribute("user") UserEntity user,Model model){
        if(repository.findByUsername(user.getUsername()) != null){
            model.addAttribute("error", "Username already exists");
            return "sign_up";
        }
        repository.save(user);
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String logout(Model model , HttpSession session){
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String showlogout(HttpServletRequest request, HttpServletResponse response , HttpSession session){
        session.removeAttribute("LoggedInEmployee");
        return "login";
    }
}

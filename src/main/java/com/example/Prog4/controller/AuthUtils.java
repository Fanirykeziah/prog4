package com.example.Prog4.controller;

import com.example.Prog4.model.UserEntity;
import jakarta.servlet.http.HttpSession;

public class AuthUtils {
    public static boolean isAuthRole(HttpSession session, String requiredRole) {
        UserEntity loggedInUser = (UserEntity) session.getAttribute("LoggedInEmployee");
        String role = String.valueOf(loggedInUser.getRole());
        return loggedInUser != null && role.equals(requiredRole);
    }

    public static boolean isAuth(HttpSession session){
        UserEntity loggedInUser = (UserEntity) session.getAttribute("LoggedInEmployee");
        return loggedInUser != null;
    }
}

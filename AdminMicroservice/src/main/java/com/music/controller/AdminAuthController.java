package com.music.controller;

import com.music.entity.Admin;
import com.music.security.JwtUtil;
import com.music.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/admin")
public class AdminAuthController {

    @Autowired
    private AdminService service;

    @Autowired
    private JwtUtil jwt;

    @GetMapping("/login")
    public String loginPage() {
        return "admin-login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response,
            Model model
    ) {
        if (!service.validateLogin(username, password)) {
            model.addAttribute("error", "Invalid credentials");
            return "admin-login";
        }

        String token = jwt.generateToken("ADMIN");

        
        Cookie cookie = new Cookie("token", token);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(3600); // 1 hour
        response.addCookie(cookie);

        model.addAttribute("admin", service.getByUsername(username));
  
        return "redirect:/songs/ui/list";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "admin-register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam String phone,
            Model model
    ) {
        try {
            Admin a = new Admin();
            a.setUsername(username);
            a.setPassword(password);
            a.setEmail(email);
            a.setPhone(phone);

            service.register(a);

            return "redirect:/admin/login";

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "admin-register";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0); 
        response.addCookie(cookie);
        return "redirect:/admin/login";
    }
}


package com.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirect root to admin home page
        return "redirect:/admin/home";
    }

    @GetMapping("/home")
    public String landing() {
        // Serve minimal home page with only Login/Register options
        return "home";
    }

    @GetMapping("/admin")
    public String adminLanding() {
        // Serve admin landing page with Login and Register options
        return "admin-landing";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        // Serve the same minimal home page under /admin/home 
        return "home";
    }
}

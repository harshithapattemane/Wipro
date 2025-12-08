package com.music.service;

import com.music.entity.Admin;
import com.music.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepo;

    @Autowired
    private PasswordEncoder encoder;

    public Admin register(Admin admin) {
        if (adminRepo.existsByUsername(admin.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        admin.setPassword(encoder.encode(admin.getPassword()));
        admin.setRole("ADMIN");

        return adminRepo.save(admin);
    }

    public boolean validateLogin(String username, String rawPassword) {
        Admin admin = adminRepo.findByUsername(username).orElse(null);
        if (admin == null) return false;

        return encoder.matches(rawPassword, admin.getPassword());
    }

    public Admin getByUsername(String username) {
        return adminRepo.findByUsername(username).orElse(null);
    }
}


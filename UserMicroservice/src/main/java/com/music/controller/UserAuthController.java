package com.music.controller;

import com.music.entity.User;
import com.music.repo.UserRepository;
import com.music.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User u) {
        User db = userRepo.findByUsername(u.getUsername()).orElse(null);

        if (db == null || !passwordEncoder.matches(u.getPassword(), db.getPassword())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(db.getUsername(), db.getId(), db.getRole());
        return ResponseEntity.ok(token);
    }
}

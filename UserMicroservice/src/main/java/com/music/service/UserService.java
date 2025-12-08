package com.music.service;

import com.music.entity.User;
import com.music.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User addUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists.");
        }
        if (userRepository.existsByEmailId(user.getEmailId())) {
            throw new RuntimeException("Email already exists.");
        }
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        if (user.getId() == null || !userRepository.existsById(user.getId())) {
            throw new RuntimeException("User with ID " + user.getId() + " does not exist.");
        }
        
        if (user.getPassword() != null && !user.getPassword().isBlank() && !isBcrypt(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("User with ID " + id + " does not exist.");
        }
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean findUser(User u) {
        return userRepository.findByUsername(u.getUsername())
                .map(db -> {
                    String enc = db.getPassword();
                    
                    if (enc == null || !isBcrypt(enc)) {
                        return false; 
                    }
                    return passwordEncoder.matches(u.getPassword(), enc);
                })
                .orElse(false);
    }
    
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    private boolean isBcrypt(String value) {
        
        return value != null && value.matches("^\\$2[aby]\\$.{56}$");
    }
}

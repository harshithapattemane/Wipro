package com.music.controller;

import com.music.entity.User;
import com.music.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            User saved = userService.addUser(user);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>((User) null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        try {
            User updated = userService.updateUser(user);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException ex) {
            if (ex.getMessage() != null && ex.getMessage().contains("does not exist")) {
                return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>((User) null, HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        boolean ok = userService.findUser(user);
        return new ResponseEntity<>(ok ? "Login OK" : "Invalid credentials", HttpStatus.OK);
    }

    @RequestMapping(value = "/users-form", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<User> addUserForm(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            @RequestParam String emailId,
            @RequestParam(required = false) String phoneNumber
    ) {
        try {
            User u = new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setRole(role);
            u.setEmailId(emailId);
            u.setPhoneNumber(phoneNumber);

            User saved = userService.addUser(u);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);

        } catch (RuntimeException ex) {
            return new ResponseEntity<>((User) null, HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/users-login-form", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<String> loginForm(
            @RequestParam String username,
            @RequestParam String password
    ) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);

        boolean ok = userService.findUser(u);
        return new ResponseEntity<>(ok ? "Login OK" : "Invalid credentials", HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable Long id) {
        try {
            User u = userService.getUserById(id);
            return new ResponseEntity<>(u, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
        }
    }
}

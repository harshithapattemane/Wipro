package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class UserRepository {

	
    @Autowired
    private final JdbcTemplate jdbcTemplate;
    
    //Dependency Injection 
    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    } 
     
    
    
    public boolean userExists(String username) {
        String sql = "SELECT COUNT(*) FROM usertable WHERE username=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count > 0;
    }

   
    public int saveUser(User user) {
        String sql = "INSERT INTO usertable (username, password) VALUES (?, ?)";
        return jdbcTemplate.update(sql, user.getUsername(), user.getPassword());
    }

    
    
    public boolean findUser(String username, String password) {
        try {
            String sql = "SELECT * FROM usertable WHERE username=? AND password=?";
            User u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username, password);
            return u != null;  // If a user is found, return true
        } catch (EmptyResultDataAccessException e) {
            return false;  // No user found, return false instead of throwing an exception
        }
    }
    
    
    
    
    
    
    
    
    
	
}
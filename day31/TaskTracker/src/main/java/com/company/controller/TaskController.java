package com.company.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.entity.Task;
import com.company.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService service;

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Task task) {
        if (service.getById(task.getId()) != null) {
            return new ResponseEntity<>("Task already exists", HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(service.addTask(task), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Task task) {
        if (service.getById(task.getId()) == null) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(service.updateTask(task), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (service.getById(id) == null) {
            return new ResponseEntity<>("Task not found", HttpStatus.NOT_FOUND);
        }
        service.deleteTask(id);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping
    public List<Task> getAll() {
        return service.getAll();
    }

    @GetMapping("/search")
    public List<Task> search(@RequestParam String status) {
        return service.searchByStatus(status);
    }

    @GetMapping("/priority/{priority}")
    public List<Task> getByPriority(@PathVariable String priority) {
        return service.getByPriority(priority);
    }

    @GetMapping("/overdue")
    public List<Task> overdue() {
        return service.overdueTasks();
    }
}

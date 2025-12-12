package com.company.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.company.entity.Task;
import com.company.repo.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository repo;

    @Override
    public Task addTask(Task task) {
        return repo.save(task);
    }

    @Override
    public Task updateTask(Task task) {
        return repo.save(task);
    }

    @Override
    public void deleteTask(Integer id) {
        repo.deleteById(id);
    }

    @Override
    public List<Task> getAll() {
        return repo.findAll();
    }

    @Override
    public Task getById(Integer id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Task> searchByStatus(String status) {
        List<Task> result = new ArrayList<>();
        for (Task t : repo.findAll()) {
            if (t.getStatus().equalsIgnoreCase(status)) {
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<Task> getByPriority(String priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : repo.findAll()) {
            if (t.getPriority().equalsIgnoreCase(priority)) {
                result.add(t);
            }
        }
        return result;
    }

    @Override
    public List<Task> overdueTasks() {
        List<Task> result = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Task t : repo.findAll()) {
            if (t.getDueDate() != null &&
                t.getDueDate().isBefore(today) &&
                !"completed".equalsIgnoreCase(t.getStatus())) {

                result.add(t);
            }
        }
        return result;
    }
}

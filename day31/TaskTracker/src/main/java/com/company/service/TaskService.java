package com.company.service;

import java.util.List;
import com.company.entity.Task;

public interface TaskService {

    Task addTask(Task task);
    Task updateTask(Task task);
    void deleteTask(Integer id);
    List<Task> getAll();
    Task getById(Integer id);
    List<Task> searchByStatus(String status);
    List<Task> getByPriority(String priority);
    List<Task> overdueTasks();
}

package com.company.entity;


import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Task {

    @Id
    private Integer id;
    private String title;
    private String description;
    private String assignedTo;
    private String priority;
    private String status;
    private LocalDate dueDate;
}

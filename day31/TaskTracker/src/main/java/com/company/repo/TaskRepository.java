package com.company.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.company.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}

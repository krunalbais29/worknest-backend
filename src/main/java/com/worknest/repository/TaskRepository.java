package com.worknest.repository;

import com.worknest.entity.Task;
import com.worknest.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAssignedTo_EmployeeId(Long employeeId);

    List<Task> findByStatus(TaskStatus status);
    
//    List<Task> getAllTasl
}
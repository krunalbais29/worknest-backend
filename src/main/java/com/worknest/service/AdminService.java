package com.worknest.service;

import java.util.*;
import com.worknest.entity.Employee;
import com.worknest.entity.Task;
import com.worknest.enums.Status;
import com.worknest.enums.TaskStatus;
import com.worknest.repository.EmployeeRepository;
import com.worknest.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final TaskRepository taskRepository;

    private final EmployeeRepository employeeRepository;

    public AdminService(EmployeeRepository employeeRepository, TaskRepository taskRepository) {
        this.employeeRepository = employeeRepository;
        this.taskRepository = taskRepository;
    }
    
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public String approveEmployee(Long employeeId) {

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setStatus(Status.ACTIVE);
        employeeRepository.save(employee);

        return "Employee approved successfully";
    }
    
    public List<Employee> getPendingEmployees() {
        return employeeRepository.findByStatus(Status.INACTIVE);
    }
    
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    
    public List<Task> getSubmittedTasks() {
        return taskRepository.findByStatus(TaskStatus.SUBMITTED);
    }
} 
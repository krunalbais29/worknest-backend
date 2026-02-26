package com.worknest.controller;

import com.worknest.dto.TaskResponse;
import com.worknest.entity.Employee;
import com.worknest.entity.Task;
import com.worknest.security.SecurityConfig;
import com.worknest.service.AdminService;
import com.worknest.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TaskService taskService;

    private final SecurityConfig securityConfig;

    private final AdminService adminService;

    public AdminController(AdminService adminService, SecurityConfig securityConfig, TaskService taskService) {
        this.adminService = adminService;
        this.securityConfig = securityConfig;
        this.taskService = taskService;
    }

    @PutMapping("/approve/{employeeId}")
    public ResponseEntity<String> approveEmployee(@PathVariable Long employeeId) {
        return ResponseEntity.ok(adminService.approveEmployee(employeeId));
    }


    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(adminService.getAllEmployees());
    }
    

    @GetMapping("/employees/pending")
    public ResponseEntity<List<Employee>> getPendingEmployees() {
        return ResponseEntity.ok(adminService.getPendingEmployees());
    }
    
    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(adminService.getAllTasks());
    } 
    
    @GetMapping("/tasks/submitted")
    public List<TaskResponse> getSubmittedTasks() {
        return taskService.getSubmittedTasks();
    }

    @PutMapping("/tasks/{taskId}/approve")
    public Task approveTask(@PathVariable Long taskId) {
        return taskService.approveTask(taskId);
    }

    @PutMapping("/tasks/{taskId}/reject")
    public Task rejectTask(@PathVariable Long taskId) {
        return taskService.rejectTask(taskId);
    }
}
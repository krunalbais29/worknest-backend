package com.worknest.controller;

import com.worknest.dto.CreateTaskRequest;
import com.worknest.dto.SubmitTaskRequest;
import com.worknest.dto.TaskResponse;
import com.worknest.dto.UpdateTaskStatusRequest;
import com.worknest.entity.Task;
import com.worknest.service.TaskService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // ADMIN → create task
    @PostMapping
    public List<Task> createTask(
            @RequestBody CreateTaskRequest request,
            Authentication authentication
    ) {
        return taskService.createTask(request, authentication.getName());
    }

    @GetMapping("/tasks")
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks();
    }

    
    @GetMapping("/my")
    public List<TaskResponse> getMyTasks(Authentication authentication) {
        return taskService.getTasksForLoggedInEmployee(authentication.getName());
    }

    // EMPLOYEE → update task status
    
    @PutMapping("/{taskId}/submit")
    public Task submitTask(
            @PathVariable Long taskId,
            @RequestBody SubmitTaskRequest request
    ) {
        return taskService.submitTask(taskId, request.getSubmissionLink());
    }
    
    
//    @PutMapping("/{taskId}/submit")
//    public Task submitTask(
//        @PathVariable Long taskId,
//        @RequestBody Map<String, String> body
//    ) {
//        return taskService.submitTask(taskId, body.get("link"));
//    }
    
    
    @PutMapping("/{taskId}/review")
    public Task reviewTask(
            @PathVariable Long taskId,
            @RequestBody UpdateTaskStatusRequest request
    ) {
        return taskService.updateTaskStatus(taskId, request);
    }
    
    
    
    @PutMapping("/{taskId}/status")
    public Task updateStatus(@PathVariable Long taskId,
                             @RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateTaskStatus(taskId, request);
    }
    
    
    @GetMapping("/submitted")
    public List<TaskResponse> getSubmittedTasks() {
        return taskService.getSubmittedTasks();
    }
}
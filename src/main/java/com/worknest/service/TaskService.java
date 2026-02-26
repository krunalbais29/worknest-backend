package com.worknest.service;

import com.worknest.dto.CreateTaskRequest;
import com.worknest.dto.TaskResponse;
import com.worknest.dto.UpdateTaskStatusRequest;
import com.worknest.entity.Employee;
import com.worknest.entity.Task;
import com.worknest.entity.User;
import com.worknest.enums.Status;
import com.worknest.enums.TaskStatus;
import com.worknest.repository.EmployeeRepository;
import com.worknest.repository.TaskRepository;
import com.worknest.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       EmployeeRepository employeeRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.employeeRepository = employeeRepository;
        this.userRepository = userRepository;
    }

    // ADMIN → create & assign task
    public List<Task> createTask(CreateTaskRequest request, String adminEmail) {

        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        List<Employee> employees;

        if (request.isAssignToAll()) {
            employees = employeeRepository.findByStatus(Status.ACTIVE);
        } else {
            employees = employeeRepository.findAllById(request.getEmployeeIds());
        }

        if (employees.isEmpty()) {
            throw new RuntimeException("No employees found");
        }

        List<Task> createdTasks = new ArrayList<>();

        for (Employee emp : employees) {
            Task task = new Task();
            task.setTitle(request.getTitle());
            task.setDescription(request.getDescription());
            task.setDueDate(request.getDueDate());
            task.setAssignedBy(admin);
            task.setAssignedTo(emp);
            task.setStatus(TaskStatus.PENDING);

            createdTasks.add(taskRepository.save(task));
        }

        return createdTasks;
    }

    // ADMIN → view all tasks
    public List<TaskResponse> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
   

    // EMPLOYEE → view own tasks
    public List<TaskResponse> getTasksForEmployee(Long employeeId) {
        return taskRepository.findByAssignedTo_EmployeeId(employeeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    
    // EMPLOYEE → update task status
    
    public Task submitTask(Long taskId, String link) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setSubmissionLink(link);
        task.setStatus(TaskStatus.SUBMITTED);

        return taskRepository.save(task);
    }
    
    
    public Task updateTaskStatu(Long taskId, UpdateTaskStatusRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(request.getStatus());

        return taskRepository.save(task);
    }

    
    
    
    @SuppressWarnings("unused")
	private TaskResponse mapToResponse(Task task) {
        TaskResponse res = new TaskResponse();

        res.setTaskId(task.getTaskId());
        res.setTitle(task.getTitle());
        res.setDescription(task.getDescription());
        res.setStatus(task.getStatus());
        res.setDueDate(task.getDueDate());
        res.setCreatedAt(task.getCreatedAt());

        res.setEmployeeId(task.getAssignedTo().getEmployeeId());
        res.setEmployeeName(task.getAssignedTo().getUser().getName());

        res.setAssignedBy(task.getAssignedBy().getName());

        return res;
    }
    
    
    public List<TaskResponse> getTasksForLoggedInEmployee(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Employee employee = employeeRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return taskRepository.findByAssignedTo_EmployeeId(employee.getEmployeeId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }
    
    
    
    
    public List<TaskResponse> getSubmittedTasks() {
        return taskRepository.findByStatus(TaskStatus.SUBMITTED)
                .stream()
                .map(TaskResponse::fromEntity)
                .toList();
    }

    
    public Task updateTaskStatus(Long taskId, UpdateTaskStatusRequest request) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setStatus(request.getStatus()); // PENDING / IN_PROGRESS / COMPLETED

        return taskRepository.save(task);
    }

	

	public Task approveTask(Long taskId) {
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));
	    task.setStatus(TaskStatus.APPROVED);
	    return taskRepository.save(task);
	}

	public Task rejectTask(Long taskId) {
	    Task task = taskRepository.findById(taskId)
	            .orElseThrow(() -> new RuntimeException("Task not found"));
	    task.setStatus(TaskStatus.REJECTED);
	    return taskRepository.save(task);
	}
    
    
    
}
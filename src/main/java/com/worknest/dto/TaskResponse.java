package com.worknest.dto;

import com.worknest.entity.Task;
import com.worknest.enums.TaskStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class TaskResponse {

    private Long taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private LocalDate dueDate;
    private LocalDateTime createdAt;

    private Long employeeId;
    private String employeeName;

    private String assignedBy;
    private String submissionLink;

    public static TaskResponse fromEntity(Task task) {
        TaskResponse dto = new TaskResponse();

        dto.setTaskId(task.getTaskId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setStatus(task.getStatus()); // ✅ enum to enum
        dto.setDueDate(task.getDueDate());
        dto.setCreatedAt(task.getCreatedAt());

        if (task.getAssignedBy() != null) {
            dto.setAssignedBy(task.getAssignedBy().getName());
        }

        if (task.getAssignedTo() != null) {
            dto.setEmployeeId(task.getAssignedTo().getEmployeeId());
            if (task.getAssignedTo().getUser() != null) {
                dto.setEmployeeName(task.getAssignedTo().getUser().getName());
            }
        }

        dto.setSubmissionLink(task.getSubmissionLink());

        return dto;
    }
}
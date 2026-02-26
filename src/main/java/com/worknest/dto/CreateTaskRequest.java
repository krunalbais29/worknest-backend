package com.worknest.dto;

import java.time.LocalDate;
import java.util.*;

public class CreateTaskRequest {
    private String title;
    private String description;
    private Long employeeId;
    private LocalDate dueDate;
    
    private List<Long> employeeIds; 
    private boolean assignToAll;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    
    public List<Long> getEmployeeIds() { return employeeIds; }

    public void setEmployeeIds(List<Long> employeeIds) { this.employeeIds = employeeIds; }

    public boolean isAssignToAll() { return assignToAll; }

    public void setAssignToAll(boolean assignToAll) { this.assignToAll = assignToAll; }
    
}
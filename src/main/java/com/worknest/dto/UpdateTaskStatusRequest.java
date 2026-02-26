package com.worknest.dto;

import com.worknest.enums.TaskStatus;

public class UpdateTaskStatusRequest {
    private TaskStatus status;

    public TaskStatus getStatus() { 
    	return status; 
    }
    
    public void setStatus(TaskStatus status) { 
    	this.status = status; 
    }
    
    
}
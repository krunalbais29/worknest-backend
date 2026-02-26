package com.worknest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;

import com.worknest.enums.TaskStatus;

@CrossOrigin(origins = "http://localhost:5173")
@Data
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String title;
    private String description;
    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "assigned_to")
    private Employee assignedTo;

    @ManyToOne
    @JoinColumn(name = "assigned_by")
    private User assignedBy; 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;


    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "submission_link")
    private String submissionLink;
    
    
    
    
}
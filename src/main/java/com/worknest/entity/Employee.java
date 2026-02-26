package com.worknest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

import com.worknest.enums.Status;

@Data
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String designation;
    private String department;
    private String phone;
    private LocalDate joiningDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    // getters & setters
}
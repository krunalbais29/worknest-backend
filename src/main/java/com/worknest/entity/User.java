package com.worknest.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import com.worknest.enums.Role;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private LocalDateTime createdAt = LocalDateTime.now();

	

    // getters & setters
}
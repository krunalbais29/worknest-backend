package com.worknest.service;

import com.worknest.dto.AuthResponse;
import com.worknest.dto.LoginRequest;
import com.worknest.dto.RegisterRequest;
import com.worknest.entity.Employee;
import com.worknest.entity.User;
import com.worknest.enums.Role;
import com.worknest.enums.Status;
import com.worknest.exception.AccountNotApprovedException;
import com.worknest.repository.EmployeeRepository;
import com.worknest.repository.UserRepository;
import com.worknest.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       EmployeeRepository employeeRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // ================= REGISTER =================
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.EMPLOYEE);

        User savedUser = userRepository.save(user);

        Employee employee = new Employee();
        employee.setUser(savedUser);
        employee.setStatus(Status.INACTIVE);
        employee.setJoiningDate(LocalDate.now());

        employeeRepository.save(employee);

        return "Registration successful. Await admin approval.";
    }

    // ================= LOGIN =================
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        Employee employee = employeeRepository.findByUser_UserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Employee record not found"));

        if (employee.getStatus() != Status.ACTIVE) {
            throw new AccountNotApprovedException("Account not approved by admin");
        }

        String token = jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        return new AuthResponse(token, "Login successful");
    }
}
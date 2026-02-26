package com.worknest.repository;

import com.worknest.entity.Employee;
import com.worknest.enums.Status;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByUser_UserId(Long userId);

	List<Employee> findByStatus(Status status);

//	List<Employee> findAllById(Long employeeId);
}
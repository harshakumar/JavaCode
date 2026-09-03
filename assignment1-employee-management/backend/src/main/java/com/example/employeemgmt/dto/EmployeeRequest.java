package com.example.employeemgmt.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * Inbound payload for create/update. Kept separate from the entity so the API
 * contract doesn't change just because the persistence model does, and so
 * validation rules live in one obvious place.
 */
@Data
public class EmployeeRequest {

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be under 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Designation is required")
    private String designation;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be a positive number")
    private Double salary;

    @NotNull(message = "Joining date is required")
    @PastOrPresent(message = "Joining date cannot be in the future")
    private LocalDate joiningDate;
}

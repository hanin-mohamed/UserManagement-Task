package com.task.employeemanagement.common.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UserCreateRequest(
        @NotBlank(message = "First name is required") String fullName,
        @NotBlank(message = "Last name is required")  String username,
        @Email @NotBlank(message = "Email is required") String email,
        @NotBlank(message = "Password is required") String password,
        @NotNull @DecimalMin(value = "0.00", message = "Salary must be >= 0")
        BigDecimal salary
) {}

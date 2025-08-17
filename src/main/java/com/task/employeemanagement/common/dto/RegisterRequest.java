package com.task.employeemanagement.common.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
    @NotBlank @Size(max=30) String fullName,
    @NotBlank @Size(max=30) String username,
    @NotBlank @Email String email,
    @NotBlank @Size(min=6, max=100) String password
){}

package com.task.employeemanagement.common.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest (
        @NotBlank String username,
        @NotBlank String password
){
}

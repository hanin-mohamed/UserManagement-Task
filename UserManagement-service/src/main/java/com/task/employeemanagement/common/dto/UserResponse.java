package com.task.employeemanagement.common.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;


public record UserResponse(
        Long id,
        String fullName,
        String username,
        String email,
        BigDecimal salary,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}

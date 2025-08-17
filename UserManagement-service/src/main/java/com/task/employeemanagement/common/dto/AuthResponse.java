package com.task.employeemanagement.common.dto;


import lombok.Builder;


@Builder
public record AuthResponse(String accessToken) {
}

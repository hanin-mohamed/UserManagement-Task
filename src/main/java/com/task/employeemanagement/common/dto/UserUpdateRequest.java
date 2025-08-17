package com.task.employeemanagement.common.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UserUpdateRequest(
       @NotNull  @DecimalMin(value = "0.00") BigDecimal salary
) {}

package com.task.employeemanagement.auth.controller;

import com.task.employeemanagement.common.dto.AppResponse;
import com.task.employeemanagement.common.dto.AuthResponse;
import com.task.employeemanagement.common.dto.LoginRequest;
import com.task.employeemanagement.common.dto.RegisterRequest;
import com.task.employeemanagement.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${app.endpoints.auth.base-uri}")
public class AuthController {

    private final AuthService authService;

    @PostMapping("${app.endpoints.auth.register-uri}")
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
         authService.register(request);
        return AppResponse.<AuthResponse>builder()
                .code(201)
                .message("Registered ")
                .data(null)
                .build();
    }

    @PostMapping("${app.endpoints.auth.login-uri}")
    public AppResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        var token = authService.login(request);
        return AppResponse.<AuthResponse>builder()
                .code(200)
                .message("Logged in")
                .data(token)
                .build();
    }
}

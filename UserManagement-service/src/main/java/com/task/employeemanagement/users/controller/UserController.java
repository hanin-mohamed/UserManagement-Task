package com.task.employeemanagement.users.controller;

import com.task.employeemanagement.common.dto.*;
import com.task.employeemanagement.users.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;


@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("${app.endpoints.users.base-uri}")
public class UserController {

    private final UserService userService;

    @GetMapping("${app.endpoints.users.get-users-uri}")
    public AppResponse<PageResponse<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        var paged = userService.listAllUsers(pageable);
        return AppResponse.<PageResponse<UserResponse>>builder()
                .code(200)
                .message("Users fetched")
                .data(PageResponse.of(paged))
                .build();
    }
    @GetMapping
    public AppResponse<PageResponse<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserResponse> paged = userService.listUsers(pageable);
        return AppResponse.<PageResponse<UserResponse>>builder()
                .code(200)
                .message("Users fetched")
                .data(PageResponse.of(paged))
                .build();
    }


    @GetMapping("${app.endpoints.users.get-user-uri}")
    public AppResponse<UserResponse> getUserById(@PathVariable Long id) {
        var user = userService.getUserById(id);
        return AppResponse.<UserResponse>builder()
                .code(200)
                .message("User fetched")
                .data(user)
                .build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse<UserResponse> createUser(@Valid @RequestBody UserCreateRequest dto) {
        var created = userService.addUser(dto);
        return AppResponse.<UserResponse>builder()
                .code(201)
                .message("User created")
                .data(created)
                .build();
    }

    @PatchMapping("${app.endpoints.users.update-user-uri}")
    public AppResponse<UserResponse> updateUser(@PathVariable Long id,
                                                @Valid @RequestBody UserUpdateRequest dto) {
        var updated = userService.updateUserDetails(id, dto);
        return AppResponse.<UserResponse>builder()
                .code(200)
                .message("User updated")
                .data(updated)
                .build();
    }

    @DeleteMapping("${app.endpoints.users.delete-user-uri}")
    public AppResponse<Void> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return AppResponse.<Void>builder()
                .code(200)
                .message("User deleted")
                .build();
    }
}

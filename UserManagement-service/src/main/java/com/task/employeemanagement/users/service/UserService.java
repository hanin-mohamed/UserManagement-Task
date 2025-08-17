package com.task.employeemanagement.users.service;

import com.task.employeemanagement.common.dto.UserCreateRequest;
import com.task.employeemanagement.common.dto.UserResponse;
import com.task.employeemanagement.common.dto.UserUpdateRequest;
import com.task.employeemanagement.users.mapper.UserMapper;
import com.task.employeemanagement.users.entity.User;
import com.task.employeemanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final UserMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse addUser(UserCreateRequest dto) {
        if (repo.existsByEmail(dto.email()))
            throw new IllegalArgumentException("Email already exists");
        if (repo.existsByUsername(dto.username()))
            throw new IllegalArgumentException("Username already exists");

        User entity = mapper.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.password()));
        User saved = repo.save(entity);
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> listAllUsers(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return mapper.toResponse(user);
    }

    @Transactional
    public UserResponse updateUserDetails(Long id, UserUpdateRequest dto) {
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setSalary(dto.salary());
        return mapper.toResponse(user);
    }

    @Transactional
    public void deleteUser(Long id) {
        if (!repo.existsById(id)) throw new IllegalArgumentException("User not found");
        repo.deleteById(id);
    }
}

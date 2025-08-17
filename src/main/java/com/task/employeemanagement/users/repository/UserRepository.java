package com.task.employeemanagement.users.repository;


import com.task.employeemanagement.users.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
   User findByUsername(String username);
    boolean existsByUsername(String username);
    Page<User> findByIdNot(Long id, Pageable pageable);
    List<User> findByIdNot(Long id);

}
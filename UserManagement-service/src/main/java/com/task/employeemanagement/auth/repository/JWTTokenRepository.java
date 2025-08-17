package com.task.employeemanagement.auth.repository;


import com.task.employeemanagement.auth.entity.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JWTTokenRepository extends JpaRepository<JWTToken,Long> {

    JWTToken findByToken(String token);
}

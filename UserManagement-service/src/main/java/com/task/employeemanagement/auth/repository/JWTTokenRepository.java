package com.task.employeemanagement.auth.repository;


import com.task.employeemanagement.auth.entity.JWTToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JWTTokenRepository extends JpaRepository<JWTToken,Long> {

    Optional<JWTToken> findByToken(String token);
}

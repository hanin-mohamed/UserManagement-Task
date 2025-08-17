package com.task.employeemanagement.auth.service;

import com.task.employeemanagement.common.dto.AuthResponse;
import com.task.employeemanagement.common.dto.LoginRequest;
import com.task.employeemanagement.common.dto.RegisterRequest;
import com.task.employeemanagement.auth.entity.JWTToken;
import com.task.employeemanagement.common.exception.custom.DuplicateResourceException;
import com.task.employeemanagement.users.entity.User;
import com.task.employeemanagement.auth.repository.JWTTokenRepository;
import com.task.employeemanagement.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final JWTTokenRepository jwtTokenRepository;

    public Void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email()))
            throw new DuplicateResourceException("Email already exists");
        if (userRepository.existsByUsername(request.username()))
            throw new DuplicateResourceException("Username already exists");

        User user = User.builder()
                .username(request.username())
                .fullName(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        return null;
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username());
        if (user == null)
            throw new BadCredentialsException("User Not Found");
        if (!passwordEncoder.matches(request.password(), user.getPassword()))
            throw new BadCredentialsException("Password Not Match ");
        return getToken(user);
    }

    private AuthResponse getToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", user.getId());

        String accessToken = jwtService.generateToken(extraClaims, user);
        Date accessTokenEXP = new Date(System.currentTimeMillis() + jwtService.getAccessTokenEXP());

        JWTToken jwtToken = JWTToken.builder()
                .token(accessToken)
                .isExpired(false)
                .expiredAt(accessTokenEXP)
                .user(user)
                .build();
        jwtTokenRepository.save(jwtToken);

        return AuthResponse.builder().accessToken(accessToken).build();
    }

}

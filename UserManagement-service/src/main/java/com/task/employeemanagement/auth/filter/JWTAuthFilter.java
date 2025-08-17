package com.task.employeemanagement.auth.filter;

import com.task.employeemanagement.auth.entity.JWTToken;
import com.task.employeemanagement.users.entity.User;
import com.task.employeemanagement.auth.repository.JWTTokenRepository;
import com.task.employeemanagement.users.repository.UserRepository;
import com.task.employeemanagement.auth.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {


    private final JWTService jwtService;
    private final UserRepository userRepository;
    private final JWTTokenRepository jwtTokenRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        String extractedUsername;
        try {
            extractedUsername = jwtService.extractUsername(token);
        } catch (io.jsonwebtoken.JwtException | IllegalArgumentException e) {
            filterChain.doFilter(request, response);
            return;
        }

        if (extractedUsername != null
                && SecurityContextHolder.getContext().getAuthentication() == null) {

           User user =
                    userRepository.findByUsername(extractedUsername);
            if (user == null) {
                filterChain.doFilter(request, response);
                return;
            }

            JWTToken storedToken =
                    jwtTokenRepository.findByToken(token);
            if (storedToken == null || storedToken.isExpired()) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.isTokenValid(token, user)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                user,
                                null,
                                user.getAuthorities()
                        );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }

}

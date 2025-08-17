package com.task.employeemanagement.auth.filter;

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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            var userOpt = userRepository.findByUsername(username);
            if (userOpt.isEmpty()) {
                filterChain.doFilter(request, response);
                return;
            }
            User user = userOpt.get();

            var tokenOpt = jwtTokenRepository.findByToken(jwt);
            if (tokenOpt.isEmpty() || tokenOpt.get().isExpired()) {
                filterChain.doFilter(request, response);
                return;
            }
            if (jwtService.isTokenValid(jwt, user)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }

}

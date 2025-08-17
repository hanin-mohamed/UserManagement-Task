package com.task.employeemanagement.users.controller;

import com.task.employeemanagement.users.entity.User;
import com.task.employeemanagement.users.service.UserExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("${app.endpoints.users.base-uri}")
public class UserExportController {

    private final UserExportService exportService;

    @GetMapping("/export")
    public ResponseEntity<Resource> exportUsersExcel(
            @AuthenticationPrincipal
            User current) throws IOException {

        byte[] bytes = exportService.exportUsersExcel(current.getId());

        String filename = "users-" +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss")) +
                ".xlsx";

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + filename + "\"")
                .header(HttpHeaders.CACHE_CONTROL, "no-store")
                .body(new ByteArrayResource(bytes));
    }

}

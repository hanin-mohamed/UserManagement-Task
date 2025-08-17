package com.task.employeemanagement.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Map;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path,
        Instant timestamp,
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Map<String, String> fieldErrors
) {
    public static ErrorResponse of(HttpStatus st, String message, String path, Map<String, String> fields) {
        return new ErrorResponse(st.value(), st.getReasonPhrase(), message, path, Instant.now(), fields);
    }
}
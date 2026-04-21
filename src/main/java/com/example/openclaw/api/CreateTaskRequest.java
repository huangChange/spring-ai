package com.example.openclaw.api;

import jakarta.validation.constraints.NotBlank;

public record CreateTaskRequest(
        @NotBlank String tenantId,
        @NotBlank String sessionId,
        @NotBlank String prompt
) {
}

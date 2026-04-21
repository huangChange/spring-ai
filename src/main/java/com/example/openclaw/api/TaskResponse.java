package com.example.openclaw.api;

import com.example.openclaw.domain.Task;

import java.time.Instant;

public record TaskResponse(
        String taskId,
        String tenantId,
        String sessionId,
        String status,
        String result,
        String errorMessage,
        Instant createdAt,
        Instant updatedAt
) {

    public static TaskResponse from(Task task) {
        return new TaskResponse(
                task.getTaskId(),
                task.getTenantId(),
                task.getSessionId(),
                task.getStatus().name(),
                task.getResult(),
                task.getErrorMessage(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }
}

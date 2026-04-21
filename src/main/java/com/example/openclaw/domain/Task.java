package com.example.openclaw.domain;

import java.time.Instant;

public class Task {

    private final String taskId;
    private final String tenantId;
    private final String sessionId;
    private final String prompt;
    private TaskStatus status;
    private String result;
    private String errorMessage;
    private final Instant createdAt;
    private Instant updatedAt;

    public Task(String taskId, String tenantId, String sessionId, String prompt) {
        this.taskId = taskId;
        this.tenantId = tenantId;
        this.sessionId = sessionId;
        this.prompt = prompt;
        this.status = TaskStatus.INIT;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void moveTo(TaskStatus next) {
        this.status = next;
        this.updatedAt = Instant.now();
    }

    public void complete(String result) {
        this.status = TaskStatus.SUCCESS;
        this.result = result;
        this.updatedAt = Instant.now();
    }

    public void fail(String errorMessage) {
        this.status = TaskStatus.FAILED;
        this.errorMessage = errorMessage;
        this.updatedAt = Instant.now();
    }

    public String getTaskId() {
        return taskId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getPrompt() {
        return prompt;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public String getResult() {
        return result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
